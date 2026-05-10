====
Command Based Programming
====
There are two approaches to making an FRC robot: **Imperative**, and **Command-Based**, of which we use the latter.


Imperative
----
Imperative is like designing a linear list of orders for the robot to follow.
It's hard to properly communicate the differences between the two philopsophies in a tl;dr format, but the cliff notes are that Imperative is what most inexperienced people would default to. While it's easier in the very short term, the drawbacks are too large to be viable; added maintenience, dependency, and ambiguity in control flow are all reasons to avoid imperative.

Command Based (and declarative)
----
Command based is like designing a set of rules for the robot to follow.
A quick distinction is in order:
"Command-Based" refers to the way robot properties and actions are broken into **subsystems** and **commands**, with a large arsenal of classes and functions to help with the subsystems and commands. "Declarative" programming is the ~~programming Paradigm~~ more sustainable, effective method of programming that goes with command-based like PB+J. For this reason, they will often be grouped together as simply "Command-Based" (occasionally, "sane, functional human being code") 

# A quick table, summing up the uses
<table>
<thead>
  <tr>
    <th>Issue</th>
    <th>Imperative</th>
    <th>Command-Based + Declarative</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td>Quick prototyping</td>
    <td>Good; makes it easy to prototype simple code</td>
    <td>Not needed; thinking about code structure for simple tests and whatnot isn't needed,</td>
  </tr>
  <tr>
    <td>dependence</td>
    <td>nightmarish; like soldering a lamp to a socket</td>
    <td>Non-existent (if you're smart) due to much more modular design</td>
  </tr>
  <tr>
    <td>Reading and comprehension</td>
    <td>Fine for short snippets of code, <br>but complex systems and developers will suffer greatly from the inherent <br>lack of clarity</td>
    <td>Easier to separate each function call and class into individual parts, <br>and debugging can be made much easier (if you're smart)</td>
  </tr>
  <tr>
    <td>Issuing Robot Orders</td>
    <td>Calling robot methods directly; easy to understand for a whole 5 seconds,<br>dependencies will FESTER</td>
    <td>Robot properties and actions are neatly fit into subsystems and commands, <br>allowing for greater control and mental ease</td>
  </tr>
  <tr>
    <td>reaction of pretentious team 1XX member</td>
    <td>dude what is this youre disgusting</td>
    <td>this is so good marry me</td>
  </tr>
</tbody>
</table>

If you yet hunger for perfection beyond Declarative, check out the SOLID principles, one of the most popular set of guidelines for clean code out there. I would recommend watching Tim Corey's videos on the principles. They're boring as *redacted*, but they are presented carefully enough to actually teach. Another caveat is that he uses C#. Even though he uses a different language, the principles still apply in Java. Also, C# and Java are very similar in basic syntax, with differences you don't need to worry about yet. 

Resources
----
1. `Command-Based Programming <https://docs.wpilib.org/en/stable/docs/software/commandbased/index.html>`_ FIRST documenation
2. `FRC 0 to Autonomous: #5 Command-Based Robot <https://youtu.be/VoxeXqy1bdQ>`_ video
3. `Overview - SwerveIO 5.0.2 User Documentation <https://javadoc.bancino.net/SwerveIO/latest/>`_

Trajectories
----
1. `Trajectory Generation and Following with WPILib <https://docs.wpilib.org/en/stable/docs/software/advanced-controls/trajectories/index.html>`_ FIRST documentation
2. `Introduction to PathWeaver <https://docs.wpilib.org/en/stable/docs/software/wpilib-tools/pathweaver/introduction.html>`_ FIRST documentation *Not using with Swerve*
3. `PathPlanner <https://github.com/mjansen4857/pathplanner/wiki>`_
4. `Ramsete Controller <https://docs.wpilib.org/en/stable/docs/software/advanced-controls/trajectories/ramsete.html>`_ The Ramsete Controller is a trajectory tracker that is built in to WPILib. This tracker can be used to accurately track trajectories with correction for minor disturbances.

Chief Delphi References
----
1. `A Problem When Using Trajectory in Swerve <https://www.chiefdelphi.com/t/a-problem-when-using-trajectory-in-a-swerve-drive/395400>`_
2. `Seeking Examples for Applying Trajectory Constraints to WPI Trajectory <https://www.chiefdelphi.com/t/seeking-examples-for-applying-trajectory-constraints-to-wpi-trajectory/392813>`_
3. `Running Multiple Cameras - USB for front and back or side viewing <https://www.chiefdelphi.com/t/is-it-possible-to-have-two-cameras/338519/2>`_
4. `3630 Stampede Robotics OffSeason Swerve Code release <https://www.chiefdelphi.com/t/3630-stampede-robotics-offseason-swerve-code-release/398713>`_
5. AUTON help `<https://www.chiefdelphi.com/t/motion-profile-in-swerve-autonomous-mode/398318/7>`_
6. `Paper: 4 wheel independent drive & independent steering "swerve" - CD-Media: Papers <https://www.chiefdelphi.com/t/paper-4-wheel-independent-drive-independent-steering-swerve/107383>`_

White Papers
----
1. `Chimera's Swerve PDF <https://www.first1684.com/uploads/2/0/1/6/20161347/chimiswerve_whitepaper__2_.pdf>`_.