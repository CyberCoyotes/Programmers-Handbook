
**FRC 3603**  
**CYBER COYOTES**

# **Introduction**

Welcome\! These Guides will help you transition from FTC-style programming to FRC Command-Based programming. If you've done FTC, you already know more than you think—many concepts transfer directly, just with different names and patterns.

## **What's in These Guides**

| Guide | What You'll Learn |
| :---- | :---- |
| [Java Refresher](?tab=t.6pvnbovjj572) | Quick recap of Java concepts, FRC naming conventions, project structure |
| [GitHub Workflow](?tab=t.5rsu87ey6kai) | Version control, branches, pull requests, issues, team conventions |
| [Command-Based Fundamentals](?tab=t.st8xvik4tbxp) | Subsystems, commands, and why we use this architecture |
| [Commands Deep Dive](?tab=t.xmu5yei4au4d) | Command factories, compositions, and advanced patterns |
| [Triggers and Bindings](?tab=t.ikku7ypns2tk) | Controller input, sensor triggers, event-driven programming |
| [State Machines](?tab=t.s7qzm4m7snuj) | When and how to use state machines in subsystems |
| Vision: Limelight |  |
| Game Tools |  |
| CTRE: ID and Firmware |  |
| CTRE: Motor Profiles |  |
| CTRE: Motor Tuning |  |
|  |  |

## **FTC → FRC: Key Differences**

You're used to FTC's **iterative/time-based** approach where your `opModeLoop()` runs continuously and you check conditions each cycle. FRC's **Command-Based** framework is *event-driven*—you declare what should happen when certain conditions are met, and the framework handles the execution.

| FTC Concept | FRC Equivalent |
| :---- | :---- |
| `opModeLoop()` checking conditions | Triggers that automatically fire commands |
| Big `if/else` chains in teleop | Button bindings in RobotContainer |
| Motor/servo wrapper classes | Subsystems |
| State variables scattered around | State machines within subsystems |
| `LinearOpMode` | Command-Based Robot project |

## **How to Use These Guides**

1. **Read in order** — Each builds on the previous  
2. **Type the code** — Don't just read, actually write it out  
3. **Ask questions** — Flag anything confusing; we'll discuss as a team  
4. **Experiment** — The best learning happens when you break things

## **Our Tech Stack**

- **Language:** Java  
- **Framework:** WPILib Command-Based with leanings toward state machines  
- **Hardware:** CTRE swerve drivetrain (Phoenix 6), Limelight 4 vision  
- **Controllers:** Xbox (standard or Elite Series 2\) for both driver and operator  
- **Autonomous Paths:** Choreo  
- **Version Control:** Git/GitHub

## **Getting Help**

- **Stuck on a concept?** Ask in myself, teammates, email, post an Issue \#getHelp  
- **Code not working?** Check the WPILib docs first, then ask  
- **Found a bug in these Guides?** Let us know so we can fix it\!

**Remember:** Every experienced programmer was once a beginner. The only bad question is the one you don't ask.  