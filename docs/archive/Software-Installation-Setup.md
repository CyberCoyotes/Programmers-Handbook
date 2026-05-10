## **Introduction**

Every season requires updates to both your computer software and robot code. This guide covers essential software updates for the FRC season that we, FRC 3603, use each season.

### **Recommended Workflow**

1. Start with primary installations

2. Install the secondary installations

3. Update motor and controller firmware

4. Install additional software tools as needed

5. Verify all installations and updates

6. Check for updates throughout the season\!

### **Primary Installations**

These are absolutely needed for every team and should be on programming laptops and driver station laptops.  
Must be downloaded annually after the game's release each January.

Capabilities Provided:

* Update RIO Image or RIO2 Image

* Configure Radio

* Run Driver's Station

* Write code for the current season

#### **FRC Game Tools**

* Install [FRC Game Tools Download \- NI](https://www.ni.com/en/support/downloads/drivers/download.frc-game-tools.html).  
  * Image your RIO or RIO2  
  * Configure your radio

#### **WPILib Suite**

* Install [WPILib Suite](https://docs.wpilib.org/en/latest/docs/zero-to-robot/step-2/wpilib-setup.html).

### **Secondary Installations and Firmware Updates**

These are likely needed by every team

#### **CTR Electronics**

* Install/Update [Phoenix Tuner X](https://apps.microsoft.com/detail/9nvv4pwdw27z?hl=en-us&gl=US). Update firmware for any of the following you are using:

  * Kraken & Falcon Motors \-v6

  * Pigeon \- v6

  * Talon SRX Controllers \- v5

  * PDP (Power Distribution Panel) unlikely you have one

#### **REV**

* Install/Update [REV Hardware Client](https://docs.revrobotics.com/rev-hardware-client). Update firmware for any of the following you are using:

  * NEO and similar Motors

  * Spark MAX Controllers

  * PDH (Power Distribution Hub)

### **Additional Software**

These may not be needed by every by team

* Download [PathPlanner](https://pathplanner.dev/home.html) for autonomous routines

* Download latest Limelight image and necessary files ([Limelight \- Downloads](https://limelightvision.io/pages/downloads))

* Update [Balena Etcher](https://etcher.balena.io/).

* Download/Updated [GitHub Desktop](https://github.com/apps/desktop) client

### **Vendor Dependencies**

After setting up a new code project each season, you need to (re)add any vendor dependencies a.k.a. Vendor Deps. Robot component manufacturers will often have specialized code libraries to use or enhance the use of their hardware. To use them you have to add their vendor deps. These can now be done directly in VS Code\!  
Here are some of the more common vendor dependencies.  
**CTRE, REV, PathPlanner, Choreo, Playing with Fusion (Time of Flight), AdvantageKit.**

This is meant as a quick reference. For full instructions, please see [Installing 3rd party libraries](https://docs.wpilib.org/en/stable/docs/software/vscode-overview/3rd-party-libraries.html).

## **Programming Resources**

### **Command-Based Programming**

1. [Command-Based Programming (WPILib)](https://docs.wpilib.org/en/stable/docs/software/commandbased/index.html)

2. [FRC 0 to Autonomous: Command-Based Programming](https://youtu.be/VoxeXqy1bdQ)

### **Motion Control**

1. [Motion Magic and Motion Profiling (CTRE)](https://docs.ctre-phoenix.com/en/stable/ch16_ClosedLoop.html)

2. [Trapezoidal Motion Profiles (WPILib)](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/controllers/trapezoidal-profiles.html)

3. [PID Theory and Robotics Explained](https://youtu.be/_bWvXn4ilrY)

### **Vision Systems**

1. [Limelight Documentation](https://docs.limelightvision.io/en/latest/)

2. [AprilTag Introduction](https://docs.wpilib.org/en/stable/docs/software/vision-processing/apriltag/index.html)

## **Example Code & References**

### **Code Examples**

1. [WPILib Example Projects](https://docs.wpilib.org/en/stable/docs/software/examples-tutorials/wpilib-examples.html)

2. [CTRE Phoenix Examples](https://github.com/CrossTheRoadElec/Phoenix6-Examples)

3. [REV Robotics Example Code](https://github.com/REVrobotics/SPARK-MAX-Examples)

### **Community Resources**

1. [Chief Delphi Programming Forum](https://www.chiefdelphi.com/c/technical/programming/55)

2. [FRC Discord](https://discord.gg/frc)

These barely scratch the surface of what's available at Chief Delphi and Github. Chief Delphi has specific threads each year about the "Open Alliance" and "The Every Bot." 

AI chat bots, like Claude and ChatGPT are also changing how we do programming research.