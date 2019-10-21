# Performing Object Detection

## Sequence of Scripts Execution

### Pre-requisite:

- Ensure that Docker Swarm is up and running on AWS Cloud

### Sequence:

- First run the Image_Processor Script on AWS Instance
- Then run the Consumer.py Script on AWS Instance
- Finally, run the Producer_camera.py script on Pi

Place an object in front of camera module and watch out for both text as well as object detection under http://broker-ip:5000
