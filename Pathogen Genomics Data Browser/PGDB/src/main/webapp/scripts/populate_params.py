import json
import sys, os

'''The "pipeline_param_file" is a dict of all parameters defined for each pipeline,  with details of tool id and 
    nested dict structure required to update the arguments within the workflow/pipeline selected.  
   
    The path to the JSON file "pipeline_param_file" is  required by set_parameters function below, which is 
    passed as the 5th argument to "run_workflow.py". 
'''

def load_json(json_file):
    with open(json_file) as f:
        return json.load(f)

def set_parameters(changed_param_dict,pipeline_param_file ):
    data = load_json(pipeline_param_file )
    for key, value in changed_param_dict.iteritems():
        d = traverse_dict(data, str(key), str(value))
    return d



def traverse_dict(data_dict, search_key, new_value):
    for key,value in data_dict.iteritems():
        if key != search_key:
            if isinstance(value,dict):          #check for dict of dict
                if search_key in value:
                    data_dict[key][search_key] = new_value
                    break
                else:
                    traverse_dict(value,search_key, new_value)
            else:
                continue                #value is a string (not a dict)
        else:
            data_dict[key] = new_value
    return data_dict


