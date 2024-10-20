resource "keycloak_openid_client" "frontend" {
  access_type = "CONFIDENTIAL"
  client_id   = "frontend"
  realm_id    = keycloak_realm.poulet.id
  valid_redirect_uris = [
    "*"
  ]
  standard_flow_enabled        = true
  direct_access_grants_enabled = true
  service_accounts_enabled     = true
  client_secret                = var.client_secret
}

resource "keycloak_role" "frontend_doctor" {
  realm_id    = keycloak_realm.poulet.id
  client_id   = keycloak_openid_client.frontend.id
  name        = "doctor"
  description = "Role with doctor privilege"
}

resource "keycloak_role" "frontend_nurse" {
  realm_id    = keycloak_realm.poulet.id
  client_id   = keycloak_openid_client.frontend.id
  name        = "nurse"
  description = "Role with nrse privilege"
}

resource "keycloak_role" "frontend_patient" {
  realm_id    = keycloak_realm.poulet.id
  client_id   = keycloak_openid_client.frontend.id
  name        = "patient"
  description = "Role with patient privilege"
}

resource "keycloak_role" "frontend_familly" {
  realm_id    = keycloak_realm.poulet.id
  client_id   = keycloak_openid_client.frontend.id
  name        = "familly"
  description = "Role with familly privilege"
}