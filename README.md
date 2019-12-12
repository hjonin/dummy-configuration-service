# DUMMY configuration service

Configuration service that has been created to group all business configuration for computation we had in our
system into one single entry point (although there is no more business meaning to this service anymore).

Before, we could find configuration in 3 different types of files:
 
* `*.csv` files, e.g. `foo_configuration.csv` (see also ` FooCalibration.java` or `FooConfiguration.java`)

| Publisher | Genre     | Cover color   |
|-----------|-----------|---------------|
| DELCOURT  | Adventure | RED           |
| *         | Adventure | GREEN         |
| *         | Fantasy   | BLUE          |
| DARGAUD   | *         | PINK          |
| DELCOURT  | *         | YELLOW        |
| *         | *         | ORANGE        |

These files are sorted by most specific configuration, determined by some columns with a weight.

Here it is determined by `Genre` and `Publisher`, `Genre` being heavier (more specific) than `Publisher`.

* `*.json` files, e.g. `consolidation.json` (see also `Consolidation.java`)

```json
{
   "consolidatedValue":"",
   "sources":[
	  {
         "name":"",
         "displayName":""
      },
	  {
         "name":"",
         "displayName":""
      }
   ],
   "conditions":[
	  "",
	  ""
   ]
}
```
 
* and `*.properties` files, e.g. `foo.properties` (see also `FooProperties`)

```properties
country=France
```

We have chosen to group the configuration into one structured model defined by the customer code, the configuration category,
type and key, and to turn the actual configuration into objects, e.g.

| customer_code   | configuration_category    | configuration_type    | configuration_key | configuration_value                         |
|-----------------|---------------------------|-----------------------|-------------------|---------------------------------------------|
| 000             | REVENUE                   | CALIBRATION           | PRICE             | {JSON serialized configuration object here} |
| PVT             | REVENUE                   | CALIBRATION           | PRICE             | {JSON serialized configuration object here} |
| 000             | REVENUE                   | CONFIGURATION         | PRICE             | {JSON serialized configuration object here} |
| PVT             | REVENUE                   | CONFIGURATION         | PRICE             | {JSON serialized configuration object here} |
| 000             | REVENUE                   | PROPERTIES            | PRICE             | {JSON serialized configuration object here} |
| PVT             | REVENUE                   | PROPERTIES            | PRICE             | {JSON serialized configuration object here} |
| 000             | REVENUE                   | CONSOLIDATION         | CONSOLIDATION     | {JSON serialized configuration object here} |
| PVT             | REVENUE                   | CONSOLIDATION         | CONSOLIDATION     | {JSON serialized configuration object here} |

We currently store this model into a MySQL DB table, but we could plug another provider to a different source.

## Usage

### Features

You can find generated API documentation (by Spring REST docs) under `/target/generated-docs` after running Maven `package` phase.

## Code

### Requirements

- A running MySQL DB *TODO use flyway to initialize*
- Set up environment variables for DB connection (see [Common settings](#Common-settings))
- Eventually add a profile (`dev`) specific `*.properties` file: `application-dev.properties` (and don't forget to activate
the profile)

### Settings

#### Common settings

| Env var         | Default  | Description             | Required |
| --------------- |:--------:| ------------------------| -------- |
| MYSQL_HOST      | ''       | MySQL database hostname | true     |
| MYSQL_SCHEMA    | ''       | MySQL database schema   | true     |
| MYSQL_PORT      | ''       | MySQL port              | true     |
| MYSQL_USER      | ''       | MySQL user              | true     |
| MYSQL_PASSWORD  | ''       | MySQL password          | true     |

### DB migration

Should be handled by flyway. *TODO*

### Run 

Just run `ConfigurationServiceApplication` (with the necessary profile if applicable).

### Deploy

*TODO*

### Security

*TODO*

## Tests

`ConfigurationControllerDocTest.java` is for documentation generation and to test the API.

Other tests are unit tests.

To make the service clearer to understand, we imagined some configuration to compute price comic books should be
sold.
It would be based on calibration that holds the average number of pages, configuration that holds the cover color,
properties that define the current country the rules apply.