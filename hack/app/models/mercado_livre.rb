require 'omniauth_mercado_livre.rb'

class MercadoLivre
  
  def initialize(access_token)
    @client = OAuth2::Client.new ENV["MERCADO_LIVRE_APP_ID"], ENV["MERCADO_LIVRE_APP_SECRET"], OmniAuth::Strategies::MercadoLivre::CLIENT_OPTIONS.dup
    @access_token = OAuth2::AccessToken.from_hash @client, :access_token => access_token, :mode => :query, :param_name => 'access_token'
  end
  
  def request(method, url, params = {})
    @client.request(method, url, params).parsed
  end
  
  def get(url, params = {})
    request :get, join_params(url, params)
  end
  
  def post(url, params = {})
    request :post, url, params
  end
  
  def put(url, params = {})
    request :put, url, params
  end
  
  def delete(url, params = {})
    request :delete, url, params
  end
  
  def request_auth(method, url, params = {})
    @access_token.request(method, url, params).parsed
  end
  
  def get_auth(url, params = {})
    request_auth :get, join_params(url, params)
  end
  
  def post_auth(url, params = {})
    request_auth :post, url, params
  end
  
  def put_auth(url, params = {})
    request_auth :put, url, params
  end
  
  def delete_auth(url, params = {})
    request_auth :delete, url, params
  end
  
  def users_me
    get_auth '/users/me'
  end
  
  def search(query)
    get '/sites/MLB/search', { :q => query }
  end
  
  private
  
  def join_params(url, params)
    url = URI.parse(url)
    url.query = URI.encode_www_form(params)
    url.to_s
  end
  
end