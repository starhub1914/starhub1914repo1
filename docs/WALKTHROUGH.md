# WALKTHROUGH GUIDE
## Software Development Document Environment (SDM)

### Step-by-Step User Walkthrough

1. **Accessing the Portal:**
   - Open browser to `http://localhost:8080/login`.
   - Toggle password visibility using the eye icon.
   - Select Local DB or LDAP mode.
   - Sign in as `admin`, `maker1`, or `checker1` with password `password123`.

2. **Exploring SDM Phase Frames:**
   - Click on Phase 1 through Phase 7 links in the left sidebar.
   - Review pre-seeded deliverable items (P101 through P702).

3. **Submitting & Approving Deliverables:**
   - Select document item (e.g., P201 Functional Specification Document).
   - Enter Maker comments and click **Submit to Checker**.
   - As Checker, review comments and click **Approve** (Hard Gate Signoff).

4. **Triggering AI Multi-Agent Pipeline & RAG Chat:**
   - On the document detail page, click **Trigger Multi-Agent Pipeline**.
   - View sequential agent handoffs (Processor -> Drafter -> Reviewer -> Log Monitor).
   - View AI recommendation and compliance review.

5. **Generating Approval Reports:**
   - On the main dashboard, click **Export Excel** or **Export PDF** to download timestamped audit reports.
