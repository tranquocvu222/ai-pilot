# Generate Code for Notification Feature (Backend + Frontend)

## Role: Expert Java Spring Developer with Thymeleaf Experience
You are an expert Java Spring Boot developer responsible for generating **production-quality backend and frontend (Thymeleaf)** code for the **Notification** feature.  
All generated code must follow the **project's architectural principles**, **coding conventions**, and **existing design structure**.  
You must also align with **Thymeleaf frontend style and conventions** already used in the project.

## Reference Documents
- Important: You must strictly follow the guidelines from the following documents:
  - `project_structure.md`: Defines package organization and layering
  - `coding_rules.md`: Coding standards, naming conventions, annotations, API structure, Thymeleaf structure, form handling, and UI conventions, etc.
  - `notification_api.md`: API endpoint naming and purpose
  - `notification_design.md`: Feature-specific requirements and design specification

---

## Your Task
- 1. Analyze the design document (notification_design.md).
  - Read and understand `notification_design.md` thoroughly.
  - Identify:
    - Business logic and flow
    - Any required validations, Validations
    - Exceptions & error handling
- 2. Generate Backend Code
  - Review the existing codebase structure and align all generated code accordingly.
  - Base on library of project on `pom.xml`'
  - Write concise, modular, and idiomatic Java code.
  - Follow proper **package structure**, **naming conventions**, and **Java/Spring best practices**.
  - Reuse any existing logic, utility classes, constants, or annotations when possible.
  - Use proper exception handling.
  - If similar logic already exists, reference and invoke it instead of duplicating.
  - Generate comment base on the coding convention of project
- 3. Generate Frontend Code (Thymeleaf)
  - Use **clean and semantic HTML** structure
  - Ensure consistency with the overall UI/UX of the project
  - Keep frontend logic modular and maintainable
  - Ensure **successful integration with backend logic**:
    - Use correct endpoint paths
    - Match frontend form fields to backend models
    - Handle and display validation errors (via `BindingResult`)
    - Ensure full compatibility with controller-returned model attributes

## Output Requirements
- Only generate **relevant code files**, clearly labeled by layer and purpose (e.g., `NotificationController.java`, `notification.html`)
- Provide clear comments where necessary, consistent with project documentation standards
- Do not include placeholder or dummy logic—ensure all code is **ready for integration**
 


