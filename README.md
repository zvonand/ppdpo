## PPiDPO

---

#### Notes
.idea directory is included to be more portable on 2 laptops

### Zookeeper
Note: Zookeeper installation is not included into this project. Launch it natively or use an official docker container.
It should run at `localhost:2181`

### Prometheus and Grafana
Prometheus and Grafana are to be installed on a machine. 
A Prometheus config used for this project can be found in root of this repo.

### ELK
Elasticsearch, Logstash and Kibana are to be installed on a local machine. 
This project will look for them on localhost on default ports (9200 for Elasticsearch, 5000 for Logstash and 5601 for Kibana).
Feed `logstash.conf` file from this repo to your Logstash installation. 