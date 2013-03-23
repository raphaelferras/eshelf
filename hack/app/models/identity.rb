class Identity < ActiveRecord::Base
  attr_accessible :uid, :provider
  belongs_to :user

  def self.initialize_with_omniauth(auth)
    Identity.new :uid => auth['uid'], :provider => auth['provider']
  end
  
  def self.find_or_initialize_with_omniauth(auth)
    find_by_provider_and_uid(auth['provider'], auth['uid']) || self.initialize_with_omniauth(auth)
  end
end
