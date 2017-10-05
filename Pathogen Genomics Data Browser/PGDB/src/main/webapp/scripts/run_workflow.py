from bioblend_functions import *
import socket, tarfile, datetime
from populate_params import *

DEBUG = True
print "DEBUG LEVEL is set as ", DEBUG
if DEBUG:
    print "Verbose mode"
else:
    print "Nothing will be printed to stdout"

def debug(s):
    if DEBUG:
        print s
    

###constants , arguments here
input_path = sys.argv[1]
output_path = sys.argv[2]
status_log_file = sys.argv[3]
param_file = sys.argv[4]
pipeline_param_file = sys.argv[5]
verbose_log_file = os.path.splitext(status_log_file)[0] + "_verbose_galaxy_log.txt"
url, api_key = "http://hydra_eph.nus.edu.sg:9090", "c5fe47950c99c52a95c6de2542599ddb"  ##old galaxy 15.03


debug("reading param_file " + param_file)
param_json = load_json(param_file)
pipeline_name = str(param_json["pipeline_name"])
debug("pipeline name "+pipeline_name)
params_dict = {}
debug("len of json " + str(len(param_json)) + " going to substitute user-given values")
if len(param_json) > 1:
    changed_params  = {}
    params_dict  = set_parameters(param_json,pipeline_param_file)    #call the param_dict function


#file to log verbose statuses
log = open(verbose_log_file,"w" , 0)
#log file to record status alone
f = open(status_log_file,"w", 0)

log.writelines("Status codes are as follows\n")
log.writelines("0: Uploaded & submitting \t 1: Running \t 2: Completed execution \t 3: Downloading \t -1: Error executing\n")
timestamp = str(datetime.datetime.now())
log.writelines("Starting workflow analysis on " + timestamp + "\n")
log.writelines("arguments given = \n")
for i in range(len(sys.argv)):
    log.writelines(str(i) + "\t" +  str(sys.argv[i]) + "\n")
basename = os.path.splitext(os.path.basename(verbose_log_file))[0]
workflow_name = pipeline_name
history_name = workflow_name + "_" + basename + "_" + socket.gethostname() + "_" + timestamp


#step1: object creation
debug("Created galaxy instance objects")
galaxyInstance, historyClient, toolClient, workflowClient, datasetClient, jobsClient = galaxy_objectSetup(url,api_key)


#step2: finding stored workflow
stored_workflow = get_workflow_byName(workflowClient, workflow_name)
workflow_id = stored_workflow["id"]
workflow_details = workflowClient.show_workflow(workflow_id)  # workflow details like input, steps, etc.,


#step3: history creation
created_history = create_newHistory(historyClient,history_name)
log.writelines("Created a new history with name " + created_history["name"] + "\n")
hid = created_history["id"]
debug("Created history with id " + hid)

#step4: upload fastq files
log.writelines("Uploading fastq files to history\n")
debug("Uploading fastq files to history")
file_extension = [".fastq", ".fq" , ".gz", ".fq.gz", ".fastq.gz"]
files = {i: os.path.join(input_path, i) for i in os.listdir(input_path) if os.path.splitext(i)[1] in file_extension}
hda = {i: upload_file_toHistory(toolClient,files[i], hid, "fastq", "H37Rv") for i in files}
files_dataset_id = {i: hda[i]["outputs"][0]["id"] for i in files}

#step5: compose a collection and create list:paired in history

#find suffix pattern  of paired file
read_r1 = re.compile(r"_R1")             #search the folder for file names with R1 and R2 in name;
read_1 = re.compile(r"_1")
fwd = ""
pattern_r1, pattern_1 = 0, 0
for i in files:
    if read_r1.search(i):
        pattern_r1 += 1
    elif read_1.search(i):
        pattern_1 += 1
if pattern_r1 == len(files)/2:
    fwd = read_r1
    debug("using pattern _R1")
else:
    fwd = read_1
    debug("using pattern _1")


paired_list = {}
for i in files:
    if fwd.search(i):
        prefix, file = i.split("_R1")[0], i
        if not prefix in paired_list:
            paired_list[prefix] = {}
        paired_list[prefix]["forward"] = file
    else:
        prefix, file = i.split("_R2")[0], i
        if not prefix in paired_list:
            paired_list[prefix] = {}
        paired_list[prefix]["reverse"] = file

paired_collection = [collections.CollectionElement(name=i, type="paired", elements=[  \
    collections.HistoryDatasetElement(name=j, id=files_dataset_id[paired_list[i][j]]) \
    for j in paired_list[i]]) for i in paired_list]

collectionDesc = collections.CollectionDescription(name="My collection list", type="list:paired",
                                                   elements=paired_collection)
dataset_collection = create_list_pairedCollection(historyClient, hid,collectionDesc)
log.writelines("Created dataset collection in history\n")
debug("Created dataset collection in history")

#step6: Invoke workflow
data_wstep_index = {"0": {"id": dataset_collection["id"], "src": "hdca"}}
if len(param_json) > 1:
    workflow_invocation = workflowClient.invoke_workflow(workflow_id, inputs=data_wstep_index, history_id=hid,params=params_dict)
    message = "Submitting workflow + edited params with invocation id "

else:
    workflow_invocation= workflowClient.invoke_workflow(workflow_id, inputs=data_wstep_index, history_id=hid)
    message = "Submitting workflow + default params with invocation id "

log.writelines(message + workflow_invocation["id"] + "\n")
debug(message + workflow_invocation["id"])
debug("status:0")
log.writelines("status:0\n")
f.writelines("status:0\n")


#step7: Poll workflow execution status and write it to log file
## {submitting(0), executing(1), completed(2), failed(-1); other status can be written to log file as such
##polls the state of each steps' job_id. This dict takes time to be updated, so it gets checked only if this dict is not empty
##exits the while loop only if all jobs for the workflow has finished
status = True
try:
    while status:
        state, jobs_state = poll_invocationStatus(workflowClient, workflow_id, workflow_invocation["id"] ,jobsClient)
        #log.writelines("polled state for = " + hid + " : " + state + "\n")
        #debug("polled state for = " + hid + " : " + state)
        #if  state == 'ok':
            #f.writelines("status:2\n")
            #log.writelines("status:2\n")
        if len(jobs_state) > 1:
            if all(i == "ok" for i in jobs_state):
                #log.writelines("all jobs finished.status:2\n")
                debug("all jobs finished. status:2")
                status = False
                break
            elif any(i == 'running' for i in jobs_state):
                f.writelines("status:1\n")
                log.writelines("status:1\n")
                for i in jobs_state:
                    log.writelines("%r\t"%i)
                log.writelines("\n")
                debug("status:1")
            elif any(i == 'error' or i == 'fail' for i in jobs_state):
                log.writelines("status:-1\n")
                f.writelines("status:-1\n")
                debug("status:-1")
            else:
                for i in jobs_state:
                    log.writelines("current job state: " + i + "\t" )
                    debug("current job state: " + i  + ",")
                log.writelines("\n")
                continue
        time.sleep(180)
except Exception:
    pass

#step8: retrieve the downloadable datasets and download them
single_dataset = get_single_dataset_inHistory(historyClient,hid)               #single datasets
collection_dataset = get_collection_dataset_inHistory(historyClient,hid)       #dataset_collection

unpack_collection_and_download_renamedDataset(historyClient,hid,datasetClient,collection_dataset,output_path)

#check if the files are downloaded in the output directory
downloaded_files = os.listdir(output_path)


#step9: compress output directory
if len(downloaded_files) > 1 and status == False:
    f.writelines("status:2\n")
    log.writelines("status:2\n")
    debug("status:2")
    log.writelines("Downloading datasets marked as available in workflow steps\n")
    debug("Downloading datasets marked as available in workflow steps")
    f.writelines("status:3\n")
    log.writelines("status:3\n")
    debug("status:3")
    for i in downloaded_files:
        log.writelines("file = " + str(i) + "\n")
        debug("file = " + str(i))
    with tarfile.open(output_path + ".tgz", "w:gz") as tar:
        tar.add(output_path, arcname = os.path.basename(output_path))
    log.writelines("Created a tar zipped file " + output_path  + ".tgz\n")
    debug("Created a tar zipped file " + output_path  + ".tgz")
else:
    log.writelines("The output folder was empty. Exiting\n")
    debug("The output folder was empty. Exiting")
    f.writelines("status:-1\n")
    debug("status:-1")

f.close()
log.writelines("DONE!!\n")
debug("DONE!!")
debug("!" + "="*50 + "****" +  "="*50 + "!")
log.writelines("!" + "="*50 + "****" +  "="*50 + "!\n")
log.close()




