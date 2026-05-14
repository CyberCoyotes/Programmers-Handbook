# Content Roadmap — Programmer's Handbook

A sequential plan for filling out and polishing the six-level Programmer's Handbook. Each phase is one branch, one pull request. Branch off `main`, complete the phase, open the PR, merge, then start the next phase. Don't skip ahead — earlier phases set conventions later phases enforce.

The phases are ordered low-risk → high-risk. The first phase is mechanical cleanup. The last phases require domain decisions. The middle phases are per-level structural conformance.

---

## How to use this with Claude Code

1. Start a Claude Code session in the repo root.
2. Point it at this file and a phase: *"Read `CONTENT_ROADMAP.md` and execute Phase 1. Open a PR when done."*
3. Claude Code creates the branch, does the work, runs `mkdocs build --strict` to verify, commits, pushes, and opens a PR with a description that lists what changed and references the phase.
4. Review the PR. Merge or request changes. Once merged, start the next phase.

Each phase below has:

- **Branch name** — exact name to use
- **Goal** — one-sentence success criterion
- **Tasks** — concrete work
- **Acceptance** — what must be true before opening the PR
- **Out of scope** — things NOT to do in this phase, to keep PRs reviewable

---

## Conventions for every PR

- Branch from latest `main`. Rebase, don't merge `main` in.
- One phase per PR. Don't combine phases.
- PR title format: `Phase N: <short description>`
- PR description must include: a checklist mapped to the phase's Acceptance criteria, and screenshots of the rendered MkDocs site for any visually-meaningful change.
- Every commit must build cleanly with `mkdocs build --strict`.
- Don't reformat untouched files. Drive-by reformatting hides real changes in diffs.

---

## Phase 1 — Audit & Cleanup

**Branch:** `content/01-audit-and-cleanup`

**Goal:** Bring filenames, navigation, and internal cross-references into a consistent state before any content work begins.

**Tasks:**

- Resolve the filename mismatch between `mkdocs.yml` nav entries (`guide-to-java-level-2-ftc.md`, `guide-to-java-level-3-ftc-advanced.md`, etc.) and the actual files in `docs/source/` (`guide-to-java-level-2.md`, etc.). Pick one convention and apply it consistently. Recommended: keep filenames short (`guide-to-java-level-N.md`) and let `mkdocs.yml` provide the descriptive nav titles.
- Audit internal "Level X" references throughout every `.md` file. Known issues: Level 3 calls itself "Level 2.5" in its learning-path diagram, Level 4 calls itself "Level 3", Level 5 calls itself "Level 4". Renumber to match the canonical 1–6 scheme used in `PROJECT.md` and `mkdocs.yml`.
- Decide what to do with the commented-out nav entries in `mkdocs.yml` (Quick Links, Getting Started, Controls, Team, Vendor Deps). Either uncomment and link to real files, or delete the commented blocks. Commented-out code in config files rots fast.
- Add a link checker to `.github/workflows/`. Use `lychee` or `mkdocs-htmlproofer-plugin`. Run it on push and PR. Fail the build on broken internal links.
- Verify `mkdocs build --strict` runs cleanly. Fix any warnings.
- Confirm `docs/archive/` content is not picked up by the site build. If it is, configure exclusions.

**Acceptance:**

- `mkdocs build --strict` exits 0.
- Every nav entry in `mkdocs.yml` resolves to a real file. No commented-out entries remain.
- No file in `docs/source/` refers to itself by the wrong level number.
- New CI workflow exists, runs on push and PR, and fails on broken internal links.

**Out of scope:** Content rewriting, adding new sections, restructuring level content. This phase is mechanical only.

---

## Phase 2 — Level 1 Structural Conformance

**Branch:** `content/02-level-1-structural-pass`

**Goal:** Bring `guide-to-java-level-1.md` into structural alignment with the standard defined in `PROJECT.md` § 9.

### The standard structure (applies to every level)

Repeated here once; later phases reference it.

1. **Header block** — Who is this for, prerequisites, time to complete.
2. **Learning path diagram** — with corrected level numbering.
3. **Numbered Parts** — each Part covers one concept group; lessons inside follow Explain → Show → Try.
4. **Coach notes** — woven throughout as `!!! note "Coach"` admonitions, not collected at the end.
5. **"Current FTC (2026)" sidebars** — where applicable, as `??? info "Current FTC (2026)"` collapsed admonitions.
6. **Capstone mini-project** — combines the level's concepts on a realistic mechanism.
7. **Reference card** — classes, methods, and patterns introduced.
8. **Bridge to next level** — what's coming and why this level prepared the student.

### Tasks for Level 1 specifically

- Verify all 8 standard sections are present. Add any missing.
- Convert any prose-style tips into `!!! note "Coach"` admonitions.
- Add a Level 1 capstone if one doesn't exist. Suggested: a small text-only project that exercises variables, control flow, methods, and arrays — e.g., a `MatchScorer` class that takes a list of match events and returns a final score. No robot hardware.
- Refine the existing "What's Next?" section into a proper bridge to Level 2. Mention specifically which Level 1 skills become which Level 2 patterns.

**Acceptance:**

- All 8 standard sections present.
- At least 3 coach-note admonitions in the body.
- Capstone is complete, types-and-runs working code with comments explaining each piece.
- `mkdocs build --strict` clean.

**Out of scope:** Levels 2–6. Major content rewrites — keep existing material if it's correct; restructure rather than replace.

---

## Phase 3 — Level 2 Structural Conformance

**Branch:** `content/03-level-2-structural-pass`

**Goal:** Bring Level 2 to the standard structure.

**Tasks:**

- Apply the 8-section standard structure.
- "Current FTC (2026)" sidebars start mattering at this level. Wherever current FTC syntax (`OpMode`, `hardwareMap`, `gamepad1.a` as boolean, etc.) differs from where the converged ecosystem is heading, add a sidebar.
- Coach notes: focus on what to demo on a real robot, common hardware-naming mistakes (case sensitivity, typos in `robotcontroller` configs), forgetting `telemetry.update()`, forgetting the `while (opModeIsActive())` loop. The existing "Common Mistakes" section is good source material — convert it into in-line coach callouts at the relevant teaching moment instead of a list at the end.
- Capstone: a single-motor intake with telemetry and a `state` field tracking whether it's intaking, holding, or stopped. The progression from "if/else with motor.setPower" → "method on a small subsystem class" should be visible.
- Bridge to Level 3: preview state machines as the answer to the tangled-boolean problem.

**Acceptance:** Standard structural checklist + at least 4 coach notes + at least 2 "Current FTC (2026)" sidebars + working capstone.

---

## Phase 4 — Level 3 Structural Conformance

**Branch:** `content/04-level-3-structural-pass`

**Goal:** Bring Level 3 to the standard structure and confirm it reads as a forward-compatible bridge.

**Tasks:**

- Apply the standard structure.
- Confirm the patterns taught here (state machines, subsystem-shaped classes, methods that look like commands) remain true after FTC convergence. Where current syntax differs, sidebar it.
- The existing state-machine material is strong — preserve it. Add a coach note about when *not* to reach for a state machine (a one-line motor toggle doesn't need one).
- Capstone: a multi-mechanism robot with at least two subsystems coordinated by a state machine — e.g., intake + arm where the arm can't move while intaking.
- Bridge to Level 4: the existing material gestures at FRC; make the bridge explicit. Call out which patterns from L3 map directly to which Level 4 concepts (state machine → command, subsystem class → `SubsystemBase`, etc.).

**Acceptance:** Standard structural checklist + the bridge explicitly previews 3 concrete L3→L4 mappings.

---

## Phase 5 — Level 4 Structural Conformance

**Branch:** `content/05-level-4-structural-pass`

**Goal:** Bring Level 4 to the standard structure. This is the FTC→FRC pivot level; treat the translation work as core content.

**Tasks:**

- Apply the standard structure.
- The existing "FTC → FRC Translation Table" is the heart of this level. Verify completeness. Add rows for things FTC veterans most often miss: `Trigger` semantics (it's not a boolean, it's a chainable object), command requirements and conflicts, why subsystems are usually singletons in FRC, the lifecycle of a default command.
- Hardware: WPILib + CTRE Talon FX / Kraken X60. Examples must compile against in-season WPILib and Phoenix 6.
- Coach notes: where new FTC-grad students typically stub their toes — forgetting `addRequirements()`, blocking calls inside command bodies, hardware-map muscle memory.
- Capstone: a single command-based subsystem with one default command and one button-bound command, on real CTRE hardware. The student should be able to clone the companion-repo project and deploy.
- Bridge to Level 5: preview command compositions and the question "how do I run a sequence?"

**Acceptance:** Standard structural checklist + translation table reviewed and complete + capstone has a corresponding companion-repo project linked.

---

## Phase 6 — Level 5 Structural Pass + Content Gap Fill

**Branch:** `content/06-level-5-structural-and-content`

**Goal:** Conform Level 5 structurally AND close the content gaps `PROJECT.md` § 4 promises but the current file doesn't fully deliver.

**Tasks:**

- Apply the standard structure.
- **Content additions required by PROJECT.md:**
  - Motion profiling and feedforward — a worked example on an elevator subsystem with at least four positions.
  - Choreo trajectory authoring — how to define a trajectory in the GUI, version the `.traj` file in the repo, and consume it in a command. Include a screenshot of Choreo for orientation.
  - Odometry as the path-following feedback signal — the swerve odometry ↔ trajectory follower relationship.
  - Fault handling and fallback states — what to do when an encoder reports nonsense or a CAN device drops off the bus.
- **Existing parts to keep:** Command Compositions, Advanced Triggers, State Machines, Lambdas, Enums with Data, Autonomous Patterns. Restructure if needed but don't delete sound content.
- Capstone: a multi-state elevator (stowed/intake/L1/L2/L3/L4) coordinated with an intake on a sequential autonomous routine that follows a Choreo path.

**Acceptance:** Standard structural checklist + each of the 4 content-addition bullets has at least one worked code example.

**Out of scope:** AdvantageKit / AdvantageScope deep-dive. PROJECT.md mentions these but they're optional at this stage; defer to a later phase.

---

## Phase 7 — Level 6 Structural Pass + Content Gap Fill

**Branch:** `content/07-level-6-structural-and-content`

**Goal:** Build out Level 6 to match `PROJECT.md` § 4 — 3D vision, AprilTag pose, vision-odometry fusion, field-relative commands.

**Tasks:**

- Apply the standard structure.
- **Content additions required by PROJECT.md:**
  - AprilTag-based pose estimation with Limelight 4 + Hailo coprocessor. Concrete configuration steps and the code that consumes the pose.
  - MegaTag-style multi-tag solving — what it is, when it helps, when it hurts, how to tune trust.
  - Fusing vision pose with swerve odometry — `SwerveDrivePoseEstimator`, standard deviations, when to trust which source.
  - Field-relative commands — a "drive to nearest scoring location" example that operates in field coordinates, with the swerve subsystem translating to motor outputs.
  - Latency compensation and recovery when a tag drops out mid-routine.
- Capstone: a vision-assisted scoring routine that uses the Level 5 path-following infrastructure and applies vision corrections when a tag is in view.

**Acceptance:** Standard structural checklist + every PROJECT.md § 4 Level-6 concept has either a worked code example or a clearly-bounded "see companion repo" link to a runnable project.

---

## Phase 8 — Companion Repo Linkage

**Branch:** `content/08-companion-repo-linkage`

**Goal:** Wire every substantive code sample in the handbook to a buildable project in the companion repo (per `PROJECT.md` § 6).

**Tasks:**

- Inventory every code block in the handbook longer than ~20 lines. For each, link to the corresponding project in the companion repo.
- Document and apply the link convention. Suggested: a footer line under each major example — `🔗 Full project: [companion-repo/level-N/example-name](url)`.
- If the companion repo doesn't exist yet, this phase scopes to: (a) creating its skeleton with one folder per level, (b) wiring CI to build every project on every commit, (c) seeding it with the existing handbook samples. Split into multiple PRs if it gets too large for one review.

**Out of scope:** Building out new examples. This phase wires what exists.

---

## Phase 9 — Coach Notes Sweep

**Branch:** `content/09-coach-notes-sweep`

**Goal:** Ensure coach-perspective callouts are present, useful, and consistent at every level.

**Tasks:**

- Review every level for coach-note density. Aim for one coach note per major Part.
- Each coach note should fall into one of four categories: (1) common student misconception, (2) "let them fail here" decision point, (3) real-robot demo opportunity, (4) hardware-specific gotcha.
- Use a consistent admonition style: `!!! note "Coach"` for general guidance, `!!! warning "Coach: common pitfall"` for things students reliably get wrong.
- No coach note should be generic ("be careful here"). Each must be specific and actionable.

**Acceptance:** Every level has at least 3 coach notes, each tagged with one of the 4 categories above (visible in the markdown source as a comment for future maintainers).

---

## Phase 10 — Final Polish

**Branch:** `content/10-final-polish`

**Goal:** Cross-cutting cleanup that makes the whole handbook read as one coherent work.

**Tasks:**

- Pick consistent terminology. Decide between "subsystem" / "mechanism", "command" / "action", "OpMode" / "program", etc. Build a small glossary in `docs/source/glossary.md` and link it from each level header.
- Consistent admonition usage across all levels.
- Code block language tags everywhere (` ```java `, not just ` ``` `).
- Image alt text where images exist.
- Spelling pass.
- Final `mkdocs build --strict`.

**Acceptance:** Reading any level in isolation feels like the same author wrote it. CI is green. No `--strict` warnings. Glossary is linked from every level.

---

## After Phase 10

- Open the handbook to outside teams and ask for review (Slack, ChiefDelphi, the FRC Discord).
- Add lightweight analytics to track which sections actually get read. Helps target the next round of revision.
- Tag a 1.0 release.
- Begin a separate roadmap for the previously-commented-out sections (Controls deep-dives, Team, Vendor Deps) if they're still in scope.

---

## Adjustments

This plan is a sketch, not a contract. Reorder phases if a different sequence makes more sense. Combine phases if two are small enough to share a PR review. Split a phase if it grows too large. The non-negotiable parts: Phase 1 comes first, level structural passes happen before content additions to that level, and companion-repo linkage (Phase 8) happens after the levels it links to are stable.