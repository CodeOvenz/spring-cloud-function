#### Introduction

This module represents a concept of a light weight AWS forwarding proxy which deploys and interacts with existing 
Spring Boot web application deployed as AWS Lambda.

A sample is provided in [sample](https://github.com/spring-cloud/spring-cloud-function/tree/serverless-web/spring-cloud-function-adapters/spring-cloud-function-adapter-aws-web/sample/pet-store) directory. It contain README and SAM template file to simplify the deployment. This module is identified as the only additional dependnecy to the existing web-app.

_NOTE: Although this module is AWS specific, this dependency is protocol only (not binary), tehrefore there is no AWS dependnecies._

The aformentioned proxy is identified as AWS Lambda [handler](https://github.com/spring-cloud/spring-cloud-function/blob/serverless-web/spring-cloud-function-adapters/spring-cloud-function-adapter-aws-web/sample/pet-store/template.yml#L14)

The main Spring Boot configuration file is identified as [MAIN_CLASS](https://github.com/spring-cloud/spring-cloud-function/blob/serverless-web/spring-cloud-function-adapters/spring-cloud-function-adapter-aws-web/sample/pet-store/template.yml#L22)
