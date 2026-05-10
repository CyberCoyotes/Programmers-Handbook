## Getting Started with Java Resources for New FRC Programmers

You can always start with the official [WPILib Documentation](https://docs.wpilib.org/en/stable/). I found it confusing years ago; too much information and too many "snippets" that never got cleaned up or explained properly. It's improved greatly over the years, but we thought we would put something together to help teams get started as there are many things NOT covered by WPILib docs.

### Key Java Concepts to Focus On

1. #### **Basic Syntax**

   - Variables and data types

   - Control flow (if/else, loops)

   - Methods and parameters

   - Basic operators

2. #### **Object-Oriented Programming**

   - Classes and objects

   - Inheritance

   - Encapsulation

   - Polymorphism

3. #### **Common Data Structures**

   - Arrays

   - ArrayLists

   - HashMaps

### Interactive Learning Platforms

1. #### [**Codecademy's Java Course**](https://www.codecademy.com/learn/learn-java)

   - Best starting point for complete beginners Highly Recommended

   - Interactive lessons

   - Free basic course, Pro version available

   - Note: Schools using "Clever" login may get PRO account free

2. #### [**Oracle's Java Tutorials**](https://docs.oracle.com/javase/tutorial/)

   - Comprehensive official documentation, good reference material

   - Focus on:

     - Object-Oriented concepts

     - Basic language features

     - Essential classes

### Video Tutorials

1. #### **FRC Specific Java**

   - [FRC 0 to Robot by Sean Sun](https://www.youtube.com/channel/UCmJAoN-yI6AJDv7JJ3372yg)

     - Excellent for FRC beginners

     - Focuses on robot programming concepts

     - Clear explanations with examples

2. #### **General Java Fundamentals**

   - [MIT OpenCourseWare \- Introduction to Programming in Java](http://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-092-introduction-to-programming-in-java-january-iap-2010/)

     - University level content

     - Well-structured lessons

     - Includes lecture notes and assignments

### Practice Exercises

1. [CodingBat Java](https://codingbat.com/java)

   - Short practice problems

   - Instant feedback

   - Gradually increasing difficulty

2. [Practice-It](https://practiceit.cs.washington.edu/)

   - Java practice problems

   - Based on University of Washington curriculum

   - Covers basic to advanced concepts

### FRC-Specific Resources

1. [WPILib Example Projects](https://docs.wpilib.org/en/stable/docs/software/examples-tutorials/wpilib-examples.html)

   - Study and modify existing robot code

   - Examples for different robot functions

   - Command-based programming examples

2. [FRC Java Programming PDF](https://docs.wpilib.org/en/stable/FRC-Java-Programming.pdf)

   - Comprehensive guide for FRC Java

   - Covers both Java basics and FRC-specific concepts

   - Good reference material

### Important Tips for Beginners

1. Start Simple

   - Begin with basic Java syntax

   - Move to FRC-specific concepts only after understanding basics

   - Don't try to understand everything at once

2. Practice Regularly

   - Write code daily, even if just for 30 minutes

   - Start with simple programs

   - Gradually increase complexity

3. Use Debug Tools

   - Learn to use VS Code debugger

   - Practice reading error messages

   - Use System.out.println() for debugging

4. Read Other Teams' Code

   - Study code from successful teams

   - Learn different approaches to similar problems

   - Don't just copy \- understand why things are done certain ways

### Github and Git

Github and Git are NOT requirements for Java or other programming in FRC, but they can be incredibly helpful. Git is like a time machine for your code and documents. Imagine you're writing a story, and every time you make significant changes, you want to save a snapshot of your work. Git does exactly that for programming projects (though it can be used for any type of files). It keeps track of every change you make, letting you:

* Save different versions of your work

* Go back to earlier versions if something goes wrong

* Work on different versions simultaneously (like having a "draft" and a "final" version)

* See what changed between versions, when it changed, and who changed it

GitHub, on the other hand, is like a social network for Git projects. It's a website where you can:

* Store your Git projects online (like storing your documents in Google Drive)

* Share your work with others

* Collaborate with other people on the same project

* Find and use code that other people have shared

* Track issues and manage projects

The main terms you'll hear:

* Repository (or "repo"): Your project folder and all its history

* Commit: A snapshot of your changes

* Branch: A separate version of your project (like a draft chapter of your story)

* Push: Uploading your changes to GitHub

* Pull: Downloading changes from GitHub

* Clone: Making a copy of a project to work on

## *Learn how to create a public repository at Github, post your code and ask other teams to help you with your code if you get stuck\!*

[Learn Git | Codecademy](https://www.codecademy.com/learn/learn-git) is helpful for concepts, but you DO NOT have to learn the command line for Git. Use the Github Desktop client to commit, push, and pull code.

## Software Installation & Setup

Every season requires updates to both your computer software and robot code. This guide covers essential software updates for the FRC season that we, FRC 3603, use each season.

### Recommended Workflow

1. Start with primary installations

2. Install the secondary installations

3. Update motor and controller firmware

4. Install additional software tools as needed

5. Verify all installations and updates

6. Check for updates throughout the season\!

### Primary Installations

These are absolutely needed for every team

| Must be downloaded annually after the game's release each January. Links will be updated then.  |
| :---- |

Capabilities Provided:

* Update RIO Image or RIO2 Image

* Configure Radio

* Run Driver's Station

* Write code for the current season

#### **FRC Game Tools**

- [ ] Install [FRC Game Tools Download \- NI](https://www.ni.com/en/support/downloads/drivers/download.frc-game-tools.html).

      - [ ] Image your RIO or RIO2

      - [ ] Configure your radio

#### **WPILib Suite**

- [ ] Install [WPILib Suite](https://docs.wpilib.org/en/latest/docs/zero-to-robot/step-2/wpilib-setup.html).

### Secondary Installations and Firmware Updates

These are likely needed by every team

#### **CTR Electronics**

- [ ] Install/Update [Phoenix Tuner X](https://apps.microsoft.com/detail/9nvv4pwdw27z?hl=en-us&gl=US). 

Update firmware for any of the following you are using:

- [ ] Kraken & Falcon Motors \-v6

      - [ ] Pigeon \- v6

      - [ ] Talon SRX Controllers \- v5

      - [ ] PDP (Power Distribution Panel) unlikely you have one

#### **REV**

- [ ] Install/Update [REV Hardware Client](https://docs.revrobotics.com/rev-hardware-client). Update firmware for any of the following you are using:

      - [ ] NEO and similar Motors

      - [ ] Spark MAX Controllers

      - [ ] PDH (Power Distribution Hub)

### Additional Software

These may not be needed by every by team

- [ ] Download [PathPlanner](https://pathplanner.dev/home.html) for autonomous routines

- [ ] Download latest Limelight image and necessary files ([Limelight \- Downloads](https://limelightvision.io/pages/downloads))

- [ ] Update [Balena Etcher](https://etcher.balena.io/).

- [ ] Download/Updated [GitHub Desktop](https://github.com/apps/desktop) client

### Vendor Dependencies

After setting up a new code project each season, you need to (re)add any vendor dependencies a.k.a. Vendor Deps. Robot component manufacturers will often have specialized code libraries to use or enhance the use of their hardware. To use them you have to add their vendor deps. Here are some of the more common vendors.

#### **CTRE**

* [https://maven.ctr-electronics.com/release/com/ctre/phoenix6/latest/Phoenix6-frc2024-latest.json](https://maven.ctr-electronics.com/release/com/ctre/phoenix6/latest/Phoenix6-frc2024-latest.json)

* [https://maven.ctr-electronics.com/release/com/ctre/phoenix/Phoenix5-frc2024-latest.json](https://maven.ctr-electronics.com/release/com/ctre/phoenix/Phoenix5-frc2024-latest.json)

#### **REV**

* [https://software-metadata.revrobotics.com/REVLib-2024.json](https://software-metadata.revrobotics.com/REVLib-2024.json)

#### **PathPlanner**

* [https://3015rangerrobotics.github.io/pathplannerlib/PathplannerLib.json](https://3015rangerrobotics.github.io/pathplannerlib/PathplannerLib.json)

#### **Choreo**

A relatively new kid on the block. We haven't used it yet, but there seems to be some excitement online

* [https://sleipnirgroup.github.io/ChoreoLib/dep/ChoreoLib.json](https://sleipnirgroup.github.io/ChoreoLib/dep/ChoreoLib.json)

#### **Time of Flight Sensor**

* [https://www.playingwithfusion.com/frc/playingwithfusion2024.json](https://www.playingwithfusion.com/frc/playingwithfusion2024.json)

This is meant as a quick reference. For full instructions, please see [Installing 3rd party libraries](https://docs.wpilib.org/en/stable/docs/software/vscode-overview/3rd-party-libraries.html).

## Programming Resources

### Command-Based Programming

1. [Command-Based Programming (WPILib)](https://docs.wpilib.org/en/stable/docs/software/commandbased/index.html)

2. [FRC 0 to Autonomous: Command-Based Programming](https://youtu.be/VoxeXqy1bdQ)

### Motion Control

1. [Motion Magic and Motion Profiling (CTRE)](https://docs.ctre-phoenix.com/en/stable/ch16_ClosedLoop.html)

2. [Trapezoidal Motion Profiles (WPILib)](https://docs.wpilib.org/en/stable/docs/software/advanced-controls/controllers/trapezoidal-profiles.html)

3. [PID Theory and Robotics Explained](https://youtu.be/_bWvXn4ilrY)

### Vision Systems

1. [Limelight Documentation](https://docs.limelightvision.io/en/latest/)

2. [PhotonVision Documentation](https://docs.photonvision.org/en/latest/)

3. [AprilTag Introduction](https://docs.wpilib.org/en/stable/docs/software/vision-processing/apriltag/index.html)

## Example Code & References

### Code Examples

1. [WPILib Example Projects](https://docs.wpilib.org/en/stable/docs/software/examples-tutorials/wpilib-examples.html)

2. [CTRE Phoenix Examples](https://github.com/CrossTheRoadElec/Phoenix6-Examples)

3. [REV Robotics Example Code](https://github.com/REVrobotics/SPARK-MAX-Examples)

### Community Resources

1. [Chief Delphi Programming Forum](https://www.chiefdelphi.com/c/technical/programming/55)

2. [FRC Discord](https://discord.gg/frc)

These barely scratch the surface of what's available at Chief Delphi and Github. Chief Delphi has specific threads each year about the "Open Alliance" and "The Every Bot." 

AI chat bots, like Claude and ChatGPT are also changing how we do programming research.

## Pre-Competition Checklist

### Hardware

1. Cables, Cords and adapters

   * USB A to B cable for RoboRIO; a.k.a. printer cable 10+ feet

   * Long ethernet cable for practice field (50ft)

   * Spare ethernet cables

   * USB to Ethernet port if your laptop doesn't have an ethernet port

   * USB hub for additional peripherals

   * Cable ties and labels for cable management

2. Power and Batteries

   * Power cords Driver Station laptop and others

   * At least 3 fully charged robot batteries

   * Battery voltage checker

   * Robot Battery charger, power strips and extension cord

   * Secure battery strap/mount replacements

3. Basic Tools and Spares

   * Allen wrench/hex key set (metric and standard)

   * Screwdrivers (Phillips and flathead)

   * Pliers and wire strippers

   * Electrical tape and zip ties

   * Spare screws, nuts, and washers

   * Spare motor controllers

   * Spare pneumatic tubing and fittings

   * Spare wheels/belts/chains

   * Basic soldering equipment

   * Spare fuses (multiple amperage ratings)

### Software

1. Firmware Verification

   - [ ] RoboRIO firmware matches current season

   - [ ] Motor controller firmware (SPARK MAX, Talon, etc.)

   - [ ] Radio programming matches current season

   - [ ] Check all CAN IDs match code configuration

   - [ ] Verify network settings match competition requirements

2. Driver Station Setup

   - [ ] Disable Windows updates

   - [ ] Disable anti-virus software on the Driver Station laptop

   - [ ] Disable sleep/hibernate modes

   - [ ] Verify correct FRC Driver Station version

   - [ ] Test all USB connections

   - [ ] Configure and test all controller mappings

   - [ ] Set team number correctly

   - [ ] Test dashboard connectivity

   - [ ] Backup Driver Station settings

3. Code Management

   - [ ] Test all autonomous routines on practice field

   - [ ] Verify sensor calibrations

   - [ ] Test fail-safe mechanisms

   - [ ] Backup code to GitHub with tagged competition version

   - [ ] USB drive with offline code backup

   - [ ] Document any known issues or workarounds

   - [ ] Print physical copy of robot code for reference (optional)

   - [ ] Print a list of autonomous programs (sketches are even better\!)

4. Communication Setup

   - [ ] Test radio connectivity at different distances

   - [ ] Verify camera feeds (if used)

   - [ ] Test emergency stop functionality

   - [ ] Configure dashboard layouts for drivers/operators

   - [ ] Test all feedback systems (LEDs, sensors)

5. Documentation

   - [ ] Print pit setup instructions

   - [ ] Competition schedule and match strategy sheets

   - [ ] Robot troubleshooting guide

   - [ ] Wiring diagram

   - [ ] Important contact numbers (mentors, technical support)

   - [ ] List of known issues and solutions

6. Practice Procedures

   - [ ] Test complete robot startup sequence

   - [ ] Practice quick battery swaps

   - [ ] Test emergency procedures

   - [ ] Practice field connection protocol

   - [ ] Verify autonomous selection workflow

   - [ ] Test communication between drive team members

7. Safety Equipment

   - [ ] Safety glasses for entire team

   - [ ] Gloves for battery handling

   - [ ] First aid kit

   - [ ] Battery spill kit

   - [ ] ESD protection for sensitive electronics

8. Pre-Match Verification

   - [ ] Create pre-match checklist

   - [ ] Battery voltage threshold defined

   - [ ] Mechanical systems check procedure

   - [ ] Communication check procedure

   - [ ] Autonomous mode selection verification

   - [ ] Driver/operator position setup

## Team History with Controls

### 2025 Season

Much of our hardware and software remains the same as 2024, we are just looking to level up this year\!

- Drivetrain: CTRE swerve \+ CTRE Kraken motors

- Gyro: CTRE Pigeon2

- Programming

  - Command Based Programming Java adapted from CTRE Generated Swerve drive

  - Improved use of CTRE control modes

  - Improved use of Path Planner

  - Improved use of Limelight

  - Improved use of LEDs for as feedback mechanism (CTRE CANdle controller)

  - Expanded use of Time of Flight (TOF) sensor "Playing with Fusion"

  - Restructuring code base to be more interchangeable from season to season, and robot to robot.

### 2024 Season

- Drivetrain: MK4i swerve \+ CTRE Kraken motors

- Gyro: CTRE Pigeon2

- Programming

  - Command Based Programming Java adapted from CTRE Generated Swerve drive

  - Improved use of CTRE control modes such as MotionMagic

  - Improved use of Path Planner

  - Improved use of Limelight

  - Improved use of LEDs for as feedback mechanism (CTRE CANdle controller)

  - Implemented Time of Flight (TOF) sensor "Playing with Fusion"

### 2023 Season

- Drivetrain: MK4i swerve \+ CTRE-Vex Falcon500 motors

- Gyro: CTRE Pigeon

- Programming:

  - Command based Java adapted from [BearSwerve](https://github.com/6391-Ursuline-Bearbotics/BearSwerve)

  - Limited use of PathPlanner during autonomous

  - Limelight never made it to the competition bot

### 2022 Season

- Drivetrain: MK3 swerve \+ CTRE-Vex Falcon500 motors

- Programming:

  - Command based Java adapted from SDS

  - Limited use of Limelight

  - Basic path planning during autonomous

  - PhotonVision never made it out of beta

### 2019 and Prior

- Drivetrain: Mecanum, tank drive, rhinotracks, or omni wheels combined with others depending on the game demands

- Drive motors: CIM motors mostly, switched to Falcons in 2019?

- Gyro: NavX

- Programming: Iterative Java
