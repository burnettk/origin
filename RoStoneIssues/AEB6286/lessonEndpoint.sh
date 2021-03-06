rm *.out

curl \
'https://biblio-qa1.dev.rosettastone.com/content/activity_set/839ca07b-d2bb-4d2b-aa79-215c8f201e28/aggregate' \
-H 'Authorization: Bearer 00eea228-c676-4162-8627-b63ceadb2bae' \
| python -mjson.tool > 1.out

curl \
'https://biblio-qa1.dev.rosettastone.com/content/lesson/839ca07b-d2bb-4d2b-aa79-215c8f201e28/aggregate' \
-H 'Authorization: Bearer 00eea228-c676-4162-8627-b63ceadb2bae' \
| python -mjson.tool > 2.out

echo "diffing..."
diff 1.out 2.out 

curl \
'https://biblio-qa1.dev.rosettastone.com/content/activity_set/839ca07b-d2bb-4d2b-aa79-215c8f201e28' \
-H 'Authorization: Bearer 00eea228-c676-4162-8627-b63ceadb2bae' \
| python -mjson.tool > 3.out

curl \
'https://biblio-qa1.dev.rosettastone.com/content/lesson/839ca07b-d2bb-4d2b-aa79-215c8f201e28' \
-H 'Authorization: Bearer 00eea228-c676-4162-8627-b63ceadb2bae' \
| python -mjson.tool > 4.out

echo "diffing..."
diff 3.out 4.out 
