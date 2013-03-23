require 'omniauth_mercado_livre.rb'

Rails.application.config.middleware.use OmniAuth::Builder do
  provider :mercado_livre, ENV["MERCADO_LIVRE_APP_ID"], ENV["MERCADO_LIVRE_APP_SECRET"]
end