require 'omniauth-oauth2'

module OmniAuth
  module Strategies
    class MercadoLivre < OmniAuth::Strategies::OAuth2
      
      CLIENT_OPTIONS = {
        :site => "https://api.mercadolibre.com",
        :authorize_url => "https://auth.mercadolivre.com.br/authorization",
      }
      
      # Give your strategy a name.
      option :name, "mercado_livre"

      # This is where you pass the options you would pass when
      # initializing your consumer from the OAuth gem.
      option :client_options, CLIENT_OPTIONS.dup

      option :provider_ignores_state, true

      # These are called after authentication has succeeded. If
      # possible, you should try to set the UID without making
      # additional calls (if the user id is returned with the token
      # or as a URI parameter). This may not be possible with all
      # providers.
      uid {
        raw_info['id']
      }

      info do
        {
          :name => raw_info['name'],
          :email => raw_info['email']
        }
      end

      extra do
        {
          'raw_info' => raw_info
        }
      end
      
      def build_access_token
        access_token = super
        access_token.options[:mode] = :query
        access_token.options[:param_name] = 'access_token'
        access_token
      end

      def raw_info
        @raw_info ||= access_token.get('/users/me').parsed
      end
    end
  end
end