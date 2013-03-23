class User < ActiveRecord::Base
  attr_accessible :name
  has_many :identities

  def self.create_with_omniauth!(auth)
    info = auth["extra"]["raw_info"]
    User.create! :name => "#{info["first_name"]} #{info["last_name"]}"
  end
end
