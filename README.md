# Asrekaf
Fake RSA Server

## Bootstrap

#### Installing SQL
To run Asrekaf from localhost, we firstly need to install and start a MySQL server.

```bash
sudo apt-get install mysql-server
sudo service mysql start
mysql -u root
``` 

Then, we should create a user and a database for the application.

```sql
create user 'asrekaf' identified by 'asrekaf';
create database asrekaf;
grant all on asrekaf.* to 'asrekaf';
```

Finally, we could execute the following query to check that our database is running on port 3306.

```sql
show variables where variable_name = 'port';
```

#### Installing Maven
Asrekaf uses Maven as a build automation tool, to manage dependencies and plugins. To run the project locally, we need to have at least version +3.X.

```bash
sudo apt-get update
sudo apt install maven
mvn --version                          # To check whether it is above 3.X
```

#### Installing dependencies
With Maven installed, getting all the project dependencies becomes easy-peasy.

```bash
mvn clean install
```