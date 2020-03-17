# awsdynamodbcredential
You can access cross account using dynamodb connector in hive


Copy the jar to hive auxlib path.

```
## You can use step to copy the jar from s3 to hive path

aws s3 cp <s3 path> .
sudo cp awsdynamodbcredential.jar /usr/lib/hive/auxlib/

```

## In hive Cli 

```
hive -hiveconf dynamodb.customAWSCredentialsProvider=software.awssupport.dynamodb.DynamoDbAwsCredentialsProvider

```

## Set crossaccount role in hive

```
set assumed.creds.role.arn=arn:aws:iam::856866798006:role/crossdynamodbtest

```

## Create a hive table for dynamodb 

CREATE EXTERNAL TABLE hive_table 
    (id string, name string,location string)
STORED BY 'org.apache.hadoop.hive.dynamodb.DynamoDBStorageHandler' 
TBLPROPERTIES (
    "dynamodb.table.name" = "emp", 
    "dynamodb.column.mapping" = "id:id,name:name,location:location"
);

select * from hive_table limit 10

You would be able to see the data from cross account.
