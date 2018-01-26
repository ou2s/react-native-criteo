
Pod::Spec.new do |s|
  s.name         = "RNCriteo"
  s.version      = "1.0.0"
  s.summary      = "RNCriteo"
  s.description  = <<-DESC
                  RNCriteo
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/author/RNCriteo.git", :tag => "master" }
  s.source_files  = "RNCriteo/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  