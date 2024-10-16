variable "keycloak_url" {
  type        = string
  description = "Keycloak URL"
}
variable "keycloak_username" {
  type        = string
  description = "Keycloak username"
}

variable "keycloak_password" {
  type        = string
  description = "Keycloak password"
}

variable "client_secret" {
  type        = string
  description = "Client secret"
  default     = "J1jZPePtgzG4Q9ltZTHlBGKEyj93P4hd"
}

variable "grafana_root_url" {
  type        = string
  description = "The root url of the grafana service"
  default     = "http://localhost:3000"
}