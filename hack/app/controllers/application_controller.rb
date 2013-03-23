class ApplicationController < ActionController::Base
  protect_from_forgery

  helper_method :all

  protected

  def mercado_livre
    @ml ||= MercadoLivre.new(access_token)
  end

  def signed_in?
    !current_user.blank?
  end

  def current_user
    @current_user ||= User.find_by_id(session[:user_id])
  end
  
  def current_user=(user)
    @current_user = user
    session[:user_id] = user ? user.id : nil
  end

  def access_token
    @access_token ||= session[:auth]
  end
  
  def access_token=(access_token)
    @access_token = access_token
    session[:auth] = access_token
  end
end
