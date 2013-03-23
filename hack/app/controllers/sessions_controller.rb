class SessionsController < ApplicationController
  
  before_filter :authenticated, :only => :destroy
  before_filter :not_authenticated, :except => :destroy
  
  def new
  end
  
  def create
    auth = request.env['omniauth.auth']
    # Find an identity here
    @identity = Identity.find_or_initialize_with_omniauth(auth)
    
    if @identity.new_record?
      @identity.user = User.create_with_omniauth!(auth)
      @identity.save!
    end
    
    self.current_user = @identity.user
    self.access_token = auth['credentials']['token']
    
    redirect_to root_url
  end

  def destroy
    self.current_user = nil
    self.access_token = nil
    redirect_to root_url
  end
end
