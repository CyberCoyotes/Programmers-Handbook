## **Overview**

Git tracks changes to your code. GitHub hosts your repository and adds collaboration features. Together, they let multiple people work on the same codebase without chaos. [Learn Git | Codecademy](https://www.codecademy.com/learn/learn-git) is helpful for concepts, but you DO NOT have to learn the command line for Git (it is kind of cool though). You can use the [Github Desktop client](https://desktop.github.com/download/) to commit, push, and pull code.

## **Core Concepts**

### **Repository (Repo)**

A folder containing your project and its entire history. Every change ever made is stored.

### **Commit**

A snapshot of your code at a moment in time. Like a save point in a video game.

### **Branch**

A parallel version of the code. Work on features without affecting the main code.

### **Main Branch**

The "official" version of the code. What actually runs on the robot.

## **The Basic Workflow**

```
1. Pull latest changes      →  Get everyone else's work
2. Create a branch          →  Make a space for your work
3. Make changes             →  Write your code
4. Commit                   →  Save your progress
5. Push                     →  Upload to GitHub
6. Create Pull Request      →  Ask to merge your work
7. Code Review              →  Team checks your work
8. Merge                    →  Your code joins main
```

## **Essential Git Commands**

### **Getting Started**

```shell
# Clone the repository (first time only)
git clone https://github.com/team-number/robot-code-2026.git

# Navigate into the folder
cd robot-code-2026
```

### **Daily Workflow**

```shell
# ALWAYS start by getting latest changes
git pull origin main

# See what branch you're on
git branch

# Create and switch to a new branch
git checkout -b feature/intake-sensor
#            ^^ creates new branch
#               ^^^^^^^^^^^^^^^^^^^^^ branch name

# Or switch to an existing branch
git checkout feature/intake-sensor
```

### **Saving Your Work**

```shell
# See what files you've changed
git status

# Stage specific files for commit
git add src/main/java/frc/robot/subsystems/Intake.java

# Or stage all changed files
git add .

# Commit with a message
git commit -m "Add beam break sensor to intake"

# Push your branch to GitHub
git push origin feature/intake-sensor
```

### **Staying Updated**

```shell
# Get latest main and merge into your branch
git checkout main
git pull origin main
git checkout feature/intake-sensor
git merge main

# Or the shorter version (from your feature branch)
git pull origin main
```

## **Branch Naming Conventions**

Use prefixes to categorize branches:

| Prefix | Use For | Example |
| ----- | ----- | ----- |
| `feature/` | New functionality | `feature/intake-sensor` |
| `fix/` | Bug fixes | `fix/drivetrain-drift` |
| `refactor/` | Code cleanup | `refactor/constants-reorganize` |
| `auto/` | Autonomous routines | `auto/three-piece-amp` |
| `test/` | Experiments | `test/new-pid-values` |

**Good branch names:**

* `feature/add-beam-break-to-intake`  
* `fix/shooter-not-reaching-rpm`  
* `auto/amp-side-two-piece`

**Bad branch names:**

* `my-stuff` (not descriptive)  
* `asdfgh` (meaningless)  
* `Feature/Intake` (wrong format)

## **Commit Message Guidelines**

### **Format**

```
<type>: <short description>

[optional longer description]
```

### **Types**

* `feat:` — New feature  
* `fix:` — Bug fix  
* `refactor:` — Code change that doesn't add features or fix bugs  
* `docs:` — Documentation only  
* `style:` — Formatting, no code change  
* `test:` — Adding tests  
* `chore:` — Maintenance tasks

### **Examples**

**Good commits:**

```
feat: Add beam break sensor to intake subsystem

fix: Correct shooter PID values for consistent RPM

refactor: Extract intake states into enum

docs: Update README with new build instructions

chore: Update WPILib to 2026.1.1
```

**Bad commits:**

```
fixed stuff                    # Too vague
WIP                            # Not descriptive
asdjkfhaskjdfh                 # Meaningless
Made a lot of changes          # What changes?
```

### **Commit Often, Commit Small**

Each commit should be one logical change:

```shell
# Good: Separate commits for separate changes
git commit -m "feat: Add beam break sensor hardware"
git commit -m "feat: Add hasTheOnePiece() method to intake"
git commit -m "feat: Add LED feedback for piece detection"

# Bad: One massive commit
git commit -m "Did a bunch of intake stuff"
```

## **Pull Requests (PRs)**

A Pull Request is a proposal to merge your branch into main. It's where code review happens.

### **Creating a Good PR**

**Title:** Clear, concise description

```
Add beam break sensor to intake
```

**Description template:**

```
## What does this PR do?
Adds a beam break sensor to detect when a game piece is held in the intake.

## Changes Made
- Added beam break sensor on DIO port 0
- Created hasTheOnePiece() method on Intake subsystem
- Added LED feedback when piece detected

## Testing Done
- Tested sensor detection with actual game piece
- Verified LED toggles correctly
- Ran through teleop cycle multiple times

## Related Issues
Closes #42
```

### **PR Checklist (before requesting review)**

* \[ \] Code compiles without errors  
* \[ \] Tested on actual robot (if possible)  
* \[ \] No commented-out code left behind  
* \[ \] Constants moved to Constants.java  
* \[ \] Meaningful variable/method names  
* \[ \] Updated any affected documentation

## **Code Review**

### **As a Reviewer**

Be constructive, not critical. Focus on:

1. **Does it work?** — Logic errors, edge cases  
2. **Is it readable?** — Could someone else understand it?  
3. **Is it maintainable?** — Will this be easy to modify later?  
4. **Does it follow conventions?** — Naming, structure, patterns

**Good review comments:**

```
"Consider renaming 'x' to 'targetPosition' for clarity"

"This could be simplified using a ternary operator, but 
the current version is more readable—your call"

"What happens if hasTheOnePiece() is called before the sensor 
initializes? Might need a null check"
```

**Bad review comments:**

```
"This is wrong"           # Not helpful - explain why
"I would do it differently"  # Suggest the alternative
"..."                      # Silent disapproval helps no one
```

### **As the Author**

* Don't take feedback personally—it's about the code, not you  
* Ask questions if you don't understand a suggestion  
* Explain your reasoning if you disagree  
* Thank reviewers for their time

## **GitHub Issues**

Issues track bugs, feature requests, and tasks.

### **Creating an Issue**

**Title:** Specific and searchable

```
Intake beam break sensor returns false positives
```

**Description:**

```
## Problem
The beam break sensor occasionally reports a piece when 
there isn't one, causing the intake to stop prematurely.

## Steps to Reproduce
1. Run intake without game piece
2. Wave hand near sensor
3. Observe false detection

## Expected Behavior
Should only detect actual game pieces

## Actual Behavior
Detects hand waves, shirt sleeves, etc.

## Possible Solution
Might need to add debouncing logic or adjust sensor position
```

### **Issue Labels**

Common labels we use:

* `bug` — Something isn't working  
* `feature` — New feature request  
* `help`  — Good for new team members  
* `priority: high` — Needs attention soon  
* `subsystem: intake` — Categorization

### **Linking Issues and PRs**

In your PR description:

```
Closes #42
Fixes #42
Resolves #42
```

This automatically closes the issue when the PR merges.

## **Common Git Scenarios**

### **"I made changes on the wrong branch"**

```shell
# Stash your changes temporarily
git stash

# Switch to correct branch
git checkout correct-branch

# Apply your changes
git stash pop
```

### **"I need to undo my last commit"**

```shell
# Undo commit but keep changes
git reset --soft HEAD~1

# Undo commit and discard changes (CAREFUL!)
git reset --hard HEAD~1
```

### **"My branch is behind main"**

```shell
# Option 1: Merge main into your branch
git checkout your-branch
git pull origin main

# Option 2: Rebase (cleaner history, but more advanced)
git checkout your-branch
git rebase main
```

### **"I have merge conflicts"**

When Git can't automatically merge:

```java
<<<<<<< HEAD
// Your code
private double speed = 0.8;
=======
// Their code
private double speed = 0.7;
>>>>>>> main
```

**To fix:**

1. Edit the file to keep what you want  
2. Remove the `<<<<<<<`, `=======`, `>>>>>>>` markers  
3. Stage and commit

```shell
git add conflicted-file.java
git commit -m "Resolve merge conflict in Intake speed"
```

## **Git Best Practices**

### **Do**

* Pull before starting work each day  
* Commit frequently with meaningful messages  
* Create branches for every feature/fix  
* Write descriptive PR descriptions  
* Review others' code thoughtfully

### **Don't**

* Commit directly to main  
* Push broken code  
* Leave PRs open for weeks  
* Ignore review feedback  
* Force push to shared branches

## **Practice Exercises**

1. **Clone and branch:**  
   * Clone a practice repository  
   * Create a branch called `feature/your-name-test`  
   * Make a small change  
   * Commit and push  
2. **Create a PR:**  
   * Write a proper title and description  
   * Request review from a teammate  
3. **Review a PR:**  
   * Find a teammate's PR  
   * Leave at least one constructive comment  
4. **Handle a conflict:**  
   * Have two people edit the same line  
   * Practice resolving the merge conflict

## **Quick Reference Card**

```shell
# Start of day
git pull origin main

# Start new work
git checkout -b feature/description

# Save progress
git add .
git commit -m "type: description"

# Share work
git push origin feature/description

# Update from main
git pull origin main

# See status
git status
git branch
git log --oneline
```

## **Key Takeaways**

* **Always branch** — Never work directly on main  
* **Commit often** — Small, logical chunks  
* **Write good messages** — Future you will thank present you  
* **Pull Requests \= collaboration** — Every merge is reviewed  
* **Issues track work** — Keep the team organized
