class Product < ActiveRecord::Base
  attr_accessible :mid, :user_id
  validates_presence_of :mid, :user_id
  
  attr_accessor :ml_data
end
