terraform {
  required_version = "1.9.5"
  required_providers {
    keycloak = {
      source  = "mrparkers/keycloak"
      version = "4.4.0"
    }
#    infisical = {
#      # version = <latest version>
#      source  = "infisical/infisical"
#      version = "0.9.0"
#    }
  }
}

provider "keycloak" {
  client_id = "admin-cli"
  username  = var.keycloak_username
  password  = var.keycloak_password
  url       = var.keycloak_url
}