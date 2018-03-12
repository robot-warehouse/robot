# An Autonomous Warehouse

This is a First-Year Collaboration Project at the University of Birmingham Computer Science course. The combined System uses 3 NXT robots to simulate 
a warehouse system, where robots go to pick-up locations, pick up items and deliver them to the drop-off point. The system is also synchronized with a 
GUI on PC side, the communication being established through Bluetooth. The route planning is smart, using the A* algorithm.

Not yet Implemented:
The robots are also able to localize themselves if they get lost using Monte-Carlo localization algorithm by particle filtering.


# Jobs/Responsibilites

* Team manager and Secondary Integrator = Augustas
* Primary integrator = Adam
* Job Assignment & Job Selection = Jordan
* W.M & Robot Interface(if Thomas does not show up) = Obaid
* Primary Netoworking = James
* Secondary Networking = Obaid
* Route Planning/ Search = Apporva
* Robot Motion-Control = Qasim
* Route Execution = Yiran Dai
* Localisation = Lican Zhao
* Route planning helper = Zhenghao Yan (if shows up)
* Robot Interface = Thomas Chan (if shows up)
* 

## For Adam

* 1) We run MotionControl on robot. Robot waits for a connection through bluetooth.
* 2) Then we run BluetoothTest class from pc part. It includes start coordinates, end coordinates which will be passed to route planning and it also send the numbers of picks to get at the pick up location.
* 3) A* plans the route, Route Execution converts it, James sends it to robot.
* 4) Robot gets to the specific location and Robot interface is started in LineFollow (currentAction==4).
* 5) In robot Interface, we check how many items are selected. If correct items are selected. Robot prints a message "To next location".
* 6) After this, it should get commands again and start moving. However, we did not make it to move again.