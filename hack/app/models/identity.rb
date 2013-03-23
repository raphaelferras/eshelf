class Identity < ActiveRecord::Base
  attr_accessible :uid, :provider
  belongs_to :user

  def self.initialize_with_omniauth(auth)
    Identity.new :uid => auth['uid'].to_s, :provider => auth['provider']
  end
  
  def self.find_or_initialize_with_omniauth(auth)
    find_by_provider_and_uid(auth['provider'], auth['uid'].to_s) || self.initialize_with_omniauth(auth)
  end
end
