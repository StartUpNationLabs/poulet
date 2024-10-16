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