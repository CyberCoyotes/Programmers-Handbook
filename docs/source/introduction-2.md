## **FTC → FRC: Key Differences**

In 2026-2027, FTC uses an **iterative/time-based** Java approach where your `opModeLoop()` runs continuously and you check conditions each cycle. FRC's **Command-Based** framework is *event-driven* — you declare what should happen when certain conditions are met, and the framework handles the execution.


| FTC Concept | FRC Equivalent |
| :---- | :---- |
| `opModeLoop()` checking conditions | Triggers that automatically fire commands |
| Big `if/else` chains in teleop | Button bindings in RobotContainer |
| Motor/servo wrapper classes | Subsystems |
| State variables scattered around | State machines within subsystems |
| `LinearOpMode` | Command-Based Robot project |