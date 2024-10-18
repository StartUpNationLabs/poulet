resource "keycloak_user" "poulet_admin" {
  realm_id = keycloak_realm.poulet.id
  username = "poulet_admin"
  enabled  = true

  email          = "admin@poulet.com"
  email_verified = true

  first_name = "John"
  last_name  = "Doe"

  initial_password {
    value     = "admin"
    temporary = false
  }
}

resource "keycloak_user_roles" "admin_role" {
  realm_id = keycloak_realm.poulet.id
  user_id  = keycloak_user.poulet_admin.id

  role_ids = [
    keycloak_role.admin_role.id
  ]
}

resource "keycloak_user" "doctor" {
  realm_id = keycloak_realm.poulet.id
  username = "doctor"
  enabled  = true

  email          = "doctor@poulet.com"
  email_verified = true

  first_name = "John"
  last_name  = "Doe"

  initial_password {
    value     = "doctor"
    temporary = false
  }
}

resource "keycloak_user_roles" "doctor_role" {
  realm_id = keycloak_realm.poulet.id
  user_id  = keycloak_user.doctor.id

  role_ids = [
    keycloak_role.doctor_role.id
  ]
}

resource "keycloak_user" "nurse" {
  realm_id = keycloak_realm.poulet.id
  username = "nurse"
  enabled  = true

  email          = "nurse@poulet.com"
  email_verified = true

  first_name = "John"
  last_name  = "Doe"

  initial_password {
    value     = "nurse"
    temporary = false
  }
}

resource "keycloak_user_roles" "nurse_role" {
  realm_id = keycloak_realm.poulet.id
  user_id  = keycloak_user.nurse.id

  role_ids = [
    keycloak_role.nurse_role.id
  ]
}

resource "keycloak_user" "patient" {
  realm_id = keycloak_realm.poulet.id
  username = "patient"
  enabled  = true

  email          = "patient@poulet.com"
  email_verified = true

  first_name = "John"
  last_name  = "Doe"

  initial_password {
    value     = "patient"
    temporary = false
  }
}

resource "keycloak_user_roles" "patient_role" {
  realm_id = keycloak_realm.poulet.id
  user_id  = keycloak_user.patient.id

  role_ids = [
    keycloak_role.patient_role.id
  ]
}

resource "keycloak_user" "familly" {
  realm_id = keycloak_realm.poulet.id
  username = "familly"
  enabled  = true

  email          = "familly@poulet.com"
  email_verified = true

  first_name = "John"
  last_name  = "Doe"

  initial_password {
    value     = "familly"
    temporary = false
  }
}

resource "keycloak_user_roles" "familly_role" {
  realm_id = keycloak_realm.poulet.id
  user_id  = keycloak_user.familly.id

  role_ids = [
    keycloak_role.familly_role.id
  ]
}