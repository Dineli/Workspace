from bioblend.galaxy import GalaxyInstance
from bioblend.galaxy.histories import HistoryClient
from bioblend.galaxy.tools import ToolClient
from bioblend.galaxy.workflows import WorkflowClient
from bioblend.galaxy.datasets import DatasetClient
from bioblend.galaxy.jobs import JobsClient
from bioblend.galaxy import dataset_collections as collections
import os, re, time, sys

####object creation functions
def galaxy_objectSetup(url,api_key):
    galaxyInstance = GalaxyInstance(url=url, key=api_key)
    historyClient = HistoryClient(galaxyInstance)
    toolClient = ToolClient(galaxyInstance)
    workflowClient = WorkflowClient(galaxyInstance)
    datasetClient = DatasetClient(galaxyInstance)
    jobsClient = JobsClient(galaxyInstance)
    return galaxyInstance, historyClient, toolClient, workflowClient, datasetClient, jobsClient

def create_newHistory(historyClient, history_name="sample"):
    return historyClient.create_history(history_name)

def upload_file_toHistory(toolClient, abs_file_path, history_id, type='fastq', dbkey='H37Rv'):
     return toolClient.upload_file(abs_file_path, history_id, type=type, dbkey=dbkey)

def create_list_pairedCollection(historyClient,history_id,collectionDesc):
    return historyClient.create_dataset_collection(history_id=history_id, collection_description=collectionDesc)



####retrieve functions
def get_history_byName(historyClient, history_name):                                              #searches list of history and returns the matching one
    return [ i for i in historyClient.get_histories() if i['name'] == history_name ][0]  #returns only single/first history (dict) named as pettern

def get_workflow_byName(workflowClient, workflow_name):                                           #searches a list of stored_workflow and returns the matching one
    return [i for i in workflowClient.get_workflows() if i['name'] == workflow_name ][0]  # returns only single/first workflow (dict) named as pettern

def get_visible_datasets_inHistory(historyClient, history_id):                                    #returns a list of history dataset marked as 'visible' during workflow creation
    return [i for i in historyClient.show_history(history_id, contents=True) if i['visible'] == True]

def get_history_datasets_byType(historyClient, history_id, dataset_type):                         #returns a list of datasets matching 'dataset' or 'dataset_colletions'
    visible_datasets = get_visible_datasets_inHistory(historyClient,history_id)
    return [i for i in visible_datasets if i['history_content_type']== dataset_type]

def get_single_dataset_inHistory(historyClient, history_id):                                      #calls def get_history_datasets_byType(historyClient, history_id, dataset_type) with 'dataset'
    return get_history_datasets_byType(historyClient, history_id,'dataset')

def get_collection_dataset_inHistory(historyClient, history_id):                                   #calls def get_history_datasets_byType(historyClient, history_id, dataset_type) with 'dataset_collection'
    return get_history_datasets_byType(historyClient, history_id,'dataset_collection')

def unpack_collection_and_download_renamedDataset(historyClient, history_id, datasetClient, collection_dataset, output_path): #unpack a collection to single datasets and rename the out files & downlaod
    for i in collection_dataset:
        temp = historyClient.show_dataset_collection(history_id, i['id'])
        for j in temp['elements']:
            if 'file_ext' in j['object']:                                                               #dict inside 'elements' key has info related to the single datasets inside collection
                if j['object']['visible'] == True:                                                      #marked as available in the workflow; since collection_datasets will have all collection
                    sample_name = j['element_identifier']
                    #extension = j['object']['file_ext']
                    rename_action = j['object']['name']
                    dataset_id = j['object']['id']
                    renamed_dataset = sample_name + "_" + rename_action
                    abs_file_path = os.path.join(output_path,renamed_dataset)
                    #print "Transferring " , renamed_dataset , " to " , abs_file_path
                    download_singleDataset(datasetClient, dataset_id,abs_file_path)

def download_singleDataset(datasetClient,dataset_id,output_path):
    datasetClient.download_dataset(dataset_id,output_path,use_default_filename=False,wait_for_completion=True)  #setting use_default_filename to False assumes that the file name is part of
                                                                                                                # the output_path(see abs_file_path in unpack function; wait_for_completion=True makes it a
                                                                                                         #blocking call and returns only after the download is complete
def poll_historyStatus(historyClient, history_id):
    return historyClient.get_status(history_id)['state']

def poll_invocationStatus(workflowClient,workflow_id,invocation_id,jobsClient):
    inv = workflowClient.show_invocation(workflow_id, invocation_id)
    return inv['state'] , [jobsClient.get_state(i['job_id']) for i in inv['steps'] if i['job_id'] is not None]




###miscellaneous
def unpack_nestedDict(input_dict,output_dict):                                                                  #unpack the dict recursively; if a key is present more than once in the nested dict, it will be overwritten with the final value
    for key,value in input_dict.iteritems():
        if isinstance(value,dict):
            unpack_nestedDict(value,output_dict)
        elif isinstance(value,list):
            for j in value:
                if isinstance(j,dict):
                    unpack_nestedDict(j,output_dict)
        else:
            output_dict.append((key,value)) #= value
