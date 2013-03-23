# encoding: UTF-8
class ApplicationController < ActionController::Base
  protect_from_forgery
  
  before_filter :authenticated

  helper_method :all

  protected

  def authenticated
    redirect_to(login_path, warning: 'Por favor faça login para continuar') unless signed_in?
  end
  
  def not_authenticated
    redirect_to(root_url, warning: 'Você não pode acessar essa página') if signed_in? 
  end
  
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
