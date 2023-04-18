# Spring Batch asynchronous job launcher example

The following example shows configuration of asynchronous Spring Batch job launcher. The result of job execution with unknown status is immediately returned to the caller.

* `POST /create-rating` runs a Spring Batch job that creates rating  
* `GET /create-rating/job-execution/{jobId}` returns job execution status  
* `GET /create-rating/{uuid}` returns a rating