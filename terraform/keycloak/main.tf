resource "keycloak_realm" "poulet" {
  realm = "poulet-realm"
}


resource "keycloak_openid_client" "poulet" {
  access_type = "PUBLIC"
  client_id   = "backend-service"
  realm_id    = keycloak_realm.poulet.id
  valid_redirect_uris = [
    "*"
  ]
  implicit_flow_enabled = true
  client_secret = var.client_secret
}