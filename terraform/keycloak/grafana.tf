resource "keycloak_openid_client" "grafana" {
  access_type = "CONFIDENTIAL"
  client_id   = "grafana"
  realm_id    = keycloak_realm.poulet.id
  valid_redirect_uris = [
    "*"
  ]
  root_url  = var.grafana_root_url
  admin_url = var.grafana_root_url
  web_origins = [
    var.grafana_root_url
  ]

  standard_flow_enabled        = true
  direct_access_grants_enabled = true
  service_accounts_enabled     = true
  client_secret                = var.client_secret
}

resource "keycloak_role" "grafana_admin" {
  realm_id    = keycloak_realm.poulet.id
  client_id   = keycloak_openid_client.grafana.id
  name        = "admin"
  description = "Role with administrator privilege"
}

resource "keycloak_role" "grafana_editor" {
  realm_id    = keycloak_realm.poulet.id
  client_id   = keycloak_openid_client.grafana.id
  name        = "editor"
  description = "Role with editor privilege"
}

resource "keycloak_role" "grafana_viewer" {
  realm_id    = keycloak_realm.poulet.id
  client_id   = keycloak_openid_client.grafana.id
  name        = "viewer"
  description = "Role with viewer privilege"
}