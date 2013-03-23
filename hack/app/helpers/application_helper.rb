module ApplicationHelper
  def signin_path(provider)
    "/auth/#{provider.to_s}"
  end
  
  def brl_currency(price)
    currency = number_to_currency(price, :unit => "R$ ", :separator => ",", :delimiter => ".")
    raw currency.gsub(/(,\d\d)$/, content_tag(:small, '\0'))
  end
end
