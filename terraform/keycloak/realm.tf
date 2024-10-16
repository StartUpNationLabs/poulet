resource "keycloak_realm" "poulet" {
  realm = "poulet-realm2"
}

resource "keycloak_role" "admin_role" {
  realm_id = keycloak_realm.poulet.id
  name     = "admin"
  composite_roles = [
    keycloak_role.grafana_admin.id,
    keycloak_role.backend_doctor.id,
    keycloak_role.frontend_doctor.id
  ]
}

resource "keycloak_role" "doctor_role" {
  realm_id = keycloak_realm.poulet.id
  name     = "doctor"
  composite_roles = [
    keycloak_role.grafana_viewer.id,
    keycloak_role.backend_doctor.id,
    keycloak_role.frontend_doctor.id
  ]
}

resource "keycloak_role" "nurse_role" {
  realm_id = keycloak_realm.poulet.id
  name     = "nurse"
  composite_roles = [
    keycloak_role.grafana_viewer.id,
    keycloak_role.backend_nurse.id,
    keycloak_role.frontend_nurse.id
  ]
}

resource "keycloak_role" "patient_role" {
  realm_id = keycloak_realm.poulet.id
  name     = "patient"
  composite_roles = [
    keycloak_role.backend_patient.id,
    keycloak_role.frontend_patient.id
  ]
}

resource "keycloak_role" "familly_role" {
  realm_id = keycloak_realm.poulet.id
  name     = "familly"
  composite_roles = [
    keycloak_role.backend_familly.id,
    keycloak_role.frontend_familly.id
  ]
}
