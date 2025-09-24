# XIDS6329-IMentorU-Foundation-DevOps
# DevOps Usage and Branching

## Branches
- main: production-ready
- develop: integration branch
- feature/*: per-developer work (e.g., feature/mfa-login)

## Pipeline
- azure-pipelines.yml builds on feature/* and develop, runs tests, creates artifact.zip, and deploys to Dev → QA → Prod.
- Configure Approvals in Pipelines → Environments → [dev|qa|prod] → Approvals & checks.

## Create artifact locally
- Bash: `./scripts/create_artifact.sh`
- PowerShell: `.\scripts\create_artifact.ps1`
