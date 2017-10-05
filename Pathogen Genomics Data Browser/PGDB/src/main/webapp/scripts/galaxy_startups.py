from bioblend_functions import *


def check_visible_collections(collectionD):
    for i in collectionD:
        temp = historyClient.show_dataset_collection(history['id'], i['id'])
        for j in temp['elements']:
            if 'file_ext' in j['object']:
                if j['object']['visible'] == True:
                    print "Visible: sample name = ", j['element_identifier'] , "-> " , "rename action = ", j['object']['name']
                else:
                    print "Invisible: sample name = ", j['element_identifier'] , i['name']

url, api_key = "http://hydra_eph.nus.edu.sg:9090", "c5fe47950c99c52a95c6de2542599ddb"  ##old galaxy 15.03
workflow_name = "mTB_pipeline"
galaxyInstance, historyClient, toolClient, workflowClient, datasetClient = galaxy_objectSetup(url,api_key)
stored_workflow = get_workflow_byName(workflowClient, workflow_name)
workflow_id = stored_workflow["id"]

history = [i for i in historyClient.get_histories() if i['name'] == "new_mTB_pipeline from DEPT-BQLB0J2" ][0]
singeD = get_single_dataset_inHistory(historyClient,history['id'])
collectionD = get_collection_dataset_inHistory(historyClient,history['id'])
check_visible_collections(collectionD)

for i in singeD:
    temp = historyClient.show_dataset(history['id'],i['id'])
    print temp['accessible'], temp['name'], temp['file_ext'], temp['visible']




