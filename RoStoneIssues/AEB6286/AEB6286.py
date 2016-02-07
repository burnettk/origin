import requests
import json
import PythonTokenGenerator

# Test environment URL
url = 'https://biblio-qa1.dev.rosettastone.com'
activity_set = '839ca07b-d2bb-4d2b-aa79-215c8f201e28'

# Set service end point, user, environment
end_point = '/content/activity_set/' + activity_set + '/aggregate/'
user = 'mhotchkiss-qa1@rosettastone.com'
environment = 'qa1'

# Get the generated userId and auth_token
response = PythonTokenGenerator.get_token(environment, user)
user_id = response[0]
auth_token = response[1]
print('auth_token = ' + auth_token + '\n')

headers = {
    'Authorization': 'Bearer ' + auth_token
}

# Make the request
r = requests.get(url + end_point + user_id, headers=headers)

# Process and format the response
results = json.loads(r.text)
pretty = json.dumps(results, indent=2)
print(pretty)

#{
#  "error_description": "not_found: missing:https://db-host-5/biblio/839ca07b-d2bb-4d2b-aa79-215c8f201e28%2Faggregate%2Fc6e3f3dd-b9dd-4c98-96a1-126190ec4927",
#  "error": "Entity Not Found"
#}
