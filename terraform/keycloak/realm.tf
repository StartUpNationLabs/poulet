resource "keycloak_realm" "poulet" {
  realm = "poulet-realm2"
}

resource "keycloak_role" "admin_role" {
  realm_id = keycloak_realm.poulet.id
  name     = "admin"
  composite_roles = [
    keycloak_role.grafana_admin.id
  ]
}

resource "keycloak_role" "doctor_role" {
  realm_id = keycloak_realm.poulet.id
  name     = "doctor"
  composite_roles = [
    keycloak_role.grafana_viewer.id
  ]
}
