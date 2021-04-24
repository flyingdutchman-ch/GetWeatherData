# Cloud Function Get Weather Data

This is a tiny cloud function written in Java Springboot that can read data from openweathermap.com API and write it to a google cloud datastore. 
It can handle the following two requests so far:

http://api.openweathermap.org/data/2.5/weather

https://api.openweathermap.org/data/2.5/onecall

See the openweathermap API description on usage (the services used are Current and One Call API):
https://openweathermap.org/api

**Important**: Please make sure to generate your own openWeatherMap API code and replace it in StoreOpenWeatherData.

## Deployment
To deploy this cloud function on google cloud just run the following command:

````
gcloud functions deploy get-openweather-current-data --entry-point functions.StoreOpenWeatherData 
--runtime java11 --trigger-http --memory 512MB --allow-unauthenticated
````

After that the function provides an HTTP endpoint which can provide an POST body in JSON 
format to provide the city you want to get data from:

````
{'city':'New York'}
````

## Run locally

````
mvn function:run
````