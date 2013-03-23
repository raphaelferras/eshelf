class ProductsController < ApplicationController
  # GET /products
  # GET /products.json
  def index
    search = mercado_livre.user_products(current_user)
    @ml_products = mercado_livre.items search["results"]
    @products = Product.where(:user_id => current_user.id)
    
    @products, @ml_products = ml_to_products(@products, @ml_products)
    puts @ml_products
    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @products }
    end
  end

  # GET /products/1
  # GET /products/1.json
  def show
    @product = Product.find(params[:id])
    
    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @product }
    end
  end

  # GET /products/new
  # GET /products/new.json
  def new
    @product = Product.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @product }
    end
  end

  # GET /products/1/edit
  def edit
    @product = Product.find(params[:id])
  end

  # POST /products
  # POST /products.json
  def create
    @product = Product.new(params[:product].merge :user_id => current_user.id)

    respond_to do |format|
      if @product.save
        format.html { redirect_to @product, notice: 'Product was successfully created.' }
        format.json { render json: @product, status: :created, location: @product }
      else
        format.html { render action: "new" }
        format.json { render json: @product.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /products/1
  # PUT /products/1.json
  def update
    @product = Product.find(params[:id])

    respond_to do |format|
      if @product.update_attributes(params[:product])
        format.html { redirect_to @product, notice: 'Product was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @product.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /products/1
  # DELETE /products/1.json
  def destroy
    @product = Product.find(params[:id])
    @product.destroy

    respond_to do |format|
      format.html { redirect_to products_url }
      format.json { head :no_content }
    end
  end
  
  def tv
    @products = Product.where(:user_id => current_user.id)
    @ml_products = mercado_livre.items @products.map{ |p| p.mid }
    
    @products, @ml_products = ml_to_products(@products, @ml_products)
    
    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @product }
    end
  end
  
  def qrcode
    @product = Product.find(params[:id])
    respond_to do |format|
      format.svg  { render :qrcode => @product.mid, :level => :l, :unit => 10 }
      format.png  { render :qrcode => @product.mid }
      format.gif  { render :qrcode => @product.mid }
      format.jpeg { render :qrcode => @product.mid }
    end
  end
  
  protected
  
  def ml_to_products(products, ml_products)
    ml_products = ml_products.inject({}) do |hash, p|
      hash[p["id"]] = p
      hash
    end
    products.each do |product|
      product.ml_data = ml_products.delete product.mid
    end
    return products, ml_products.values
  end
  
end
