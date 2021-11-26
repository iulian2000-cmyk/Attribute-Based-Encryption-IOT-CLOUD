# Attribute-Based-Encryption IOT-CLOUD

## I. Purpose 
> This project was developed for education purpose only . It is an attempt to implement the PP-CP-ABE scheme in a specific context ,using Java and JPBC as main library . The goal of the project was to implement the mechanism behind encrypted storage on Cloud . 

## II . Context.
> Let's say that we have a number of companies,individuals,organizations whose data are stored encrypted on Cloud . This mechanism uses also elements from 
access control , mainly a variation from RBAC to ABAC . The idea is to ensure confidentiality , integrity and availabilty to our data which is stored . 
Each company,individual,organization defines a an access policy . An access policy is a method to describe the properties on which a user must satisfy in order to have access the data of another user . 

> In our system an user can have one or multiple roles ,listed below :
> - A = EMPLOYEE_PRODUCTION
> - B = LEADER_PRODUCTION 
> - C = DIRECTOR_PRODUCTION 
> - D = ACCOUNTANT
> - E = CHIEF_ACCOUNTANT
> - F = DIRECTOR_ECONOMIC
> - G = DIRECTOR
> - H = ADMINISTRATOR
> - I = CEO
> - J = SECURITY_ADMIN 
>  -K = ACCORD_SUPERIOR.YES,ACCORD_SUPERIOR.NO
>  -L = INDIVIDUAL 

> In other words , an access policy can be seen as a formula from propositional logic , in which the variables are from the described list and the logical operators allowed are OR and AND  . 
> Example : ( A AND B) OR C 
