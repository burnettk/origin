import requests
import json
import PythonTokenGenerator

# cURL command to be converted
# curl -X GET -H "Accept: application/json" -H "Authorization: Bearer $auth_token" $bib_host/content/user_activity_set_summary/$user_id

# Test environment URL
url = "https://biblio-qa1.dev.rosettastone.com"

# Set service end point, user, environment
end_point = "/content/user_activity_set_summary/"
user = "mhotchkiss-qa1@rosettastone.com"
environment = "qa1"

# Get the generated userId and auth_token
response = PythonTokenGenerator.get_token(environment, user)
user_id = response[0]
auth_token = response[1]
print("auth_token + " + auth_token)

headers = {
    'Accept': 'application/json',
    'Authorization': 'Bearer ' + auth_token
}

# Make the request
r = requests.get(url + end_point + user_id, headers=headers)

# Process and format the response
results = json.loads(r.text)
pretty = json.dumps(results, indent=2)
print(pretty)


