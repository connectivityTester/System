<?php
      
class VariableM
{
   protected $mType = null;
   protected $mName = null;
   protected $mVisibility = null;
   protected $mInitialValue = null;
   protected $mIsConst = null;
   protected $mIsStatic = null;

   static public function createAsInitList ( &$iVariablesArray )
   {
     // Logger::log("VariableM", "Method createAsInitList started");
      if ( $iVariablesArray === null ) throw new \InvalidArgumentException("Initialization list for method is null");
      if ( !is_array($iVariablesArray) ) throw new \InvalidArgumentException("Initialization list for method is not array");

      $vars = array();
      foreach ( $iVariablesArray as $pkey => &$param )
      {
         //Logger::log("VariableM","Processing entity variable '$pkey'");
         $vars[] = self::createInitItem( $param );
      }

      return $vars;
   }

   static private function createInitItem( &$iVariableArray )
   {
     // Logger::log("VariableM", "Method createInitItem started");
      self::validateVariableArray( $iVariableArray, true, false, false, false, true, false );
      
      $name = &$iVariableArray['Name'];
      $visibility = new VisibilityM(VisibilityM::UNKNOWN);
      $isConstant = false; 
      $type = null;
      $value = &$iVariableArray['Value'];
      $isStatic = false; 
      
      return new VariableM($type, $name, $visibility, $isConstant, $value, $isStatic);
   }
   
   static public function createAsTypeParametrs( &$iVariablesArray )
   {
     // Logger::log("VariableM", "Method createAsTypeParametrs started");
      if ( $iVariablesArray === null ) throw new \InvalidArgumentException("Type Parameters description for entity is null");
      if ( !is_array($iVariablesArray) ) throw new \InvalidArgumentException("Type Parameters description for entity is not array");

      $vars = array();
      foreach ( $iVariablesArray as $pkey => &$param )
      {
         Logger::log("VariableM","Processing entity variable '$pkey'");
         $vars[] = self::createAsTypeParametr( $param );
      }

      return $vars;
   }

   static private function createAsTypeParametr( &$iVariableArray )
   {
    //  Logger::log("VariableM", "Method createAsTypeParametr started");
      self::validateVariableArray( $iVariableArray, true, false, false, true, false, false );
      
      $name = &$iVariableArray['Name'];
      $visibility = new VisibilityM(VisibilityM::UNKNOWN);
      $isConstant = false; // converting explicitly to value of type bool
      $type = &$iVariableArray['Type'];
      $value = &$iVariableArray['Value'];
      $isStatic = false; 
      
      return new VariableM($type, $name, $visibility, $isConstant, $value, $isStatic);
   }
   

   static public function createAsEntityVariables( &$iVariablesArray )
   {
   //  Logger::log("VariableM", "Method createAsEntityVariables started");
      if ( $iVariablesArray === null ) throw new \InvalidArgumentException("Variable description for entity is null");
      if ( !is_array($iVariablesArray) ) throw new \InvalidArgumentException("Variable description for entity is not array");

      $vars = array();
      foreach ( $iVariablesArray as $pkey => &$param )
      {
         Logger::log("VariableM","Processing entity variable '$pkey'");
         $vars[] = self::createAsEntityVariable( $param );
      }

      return $vars;
   }
   static public function createAsEntityVariable( &$iVariableArray )
   {
   //   Logger::log("VariableM", "Method createAsEntityVariable started");
   	self::validateVariableArray( $iVariableArray, true, true, true, true, true, true );
   	
   	$name = &$iVariableArray['Name'];
   	$visibility = new VisibilityM ( $iVariableArray['Visibility'] );
   	$isConstant = $iVariableArray['IsConstant'] ? true : false; // converting explicitly to value of type bool
   	$type = &$iVariableArray['Type'];
   	$value = &$iVariableArray['Value'];
      $isStatic = $iVariableArray['IsStatic'] ? true : false; 
   	
   	return new VariableM($type, $name, $visibility, $isConstant, $value, $isStatic);
   }
   
   static public function createAsMethodParameters( &$iParametersArray )
   {
    //  Logger::log("VariableM", "Method createAsMethodParameters started");
      if ( $iParametersArray === null ) throw new \InvalidArgumentException("Parameters description is null");
      if ( !is_array($iParametersArray) ) throw new \InvalidArgumentException("Parameters description is not array");

      $vars = array();
      foreach ( $iParametersArray as $pkey => &$param )
      {
        // Logger::log("VariableM","Processing parameter '$pkey'");
         $vars[] = self::createAsMethodParameter( $param );
      }

      return $vars;
   }
   static public function createAsMethodParameter( &$iVariableArray )
   {
    //  Logger::log("VariableM", "Method createAsMethodReturnValue started");
   	//sLogger::log("VariableM", "Parameter name - " . $iVariableArray['Name']);
      self::validateVariableArray( $iVariableArray, true, false, true, true, true, false );
   	$name = &$iVariableArray['Name'];
   	$visibility = new VisibilityM(VisibilityM::UNKNOWN);
   	$isConstant = $iVariableArray['IsConstant'] ? true : false; // converting explicitly to value of type bool
   	$type = &$iVariableArray['Type'];
   	$value = &$iVariableArray['Value'];
      $isStatic = false;
   	return new VariableM($type, $name, $visibility, $isConstant, $value, $isStatic);
   }
   
   /*
    * Example variable description for variable as class member:
		   IsConstant: 1
		   Type: string
   */
   static public function createAsMethodReturnValue( &$iVariableArray )
   {
   //   Logger::log("VariableM", "Method createAsMethodReturnValue started");
   	self::validateVariableArray( $iVariableArray, false, false, true, true, false, false );
   	
   	$name = null;
   	$visibility = new VisibilityM(VisibilityM::UNKNOWN);
   	$isConstant = $iVariableArray['IsConstant'] ? true : false; // converting explicitly to value of type bool
   	$type = &$iVariableArray['Type'];
      $isStatic = false; 
   	$value = null;
   	
   	return new VariableM($type, $name, $visibility, $isConstant, $value, $isStatic);
   }
   
   static private function validateVariableArray( &$iVariableArray, 
                                                   $iValidateName, 
                                                   $iValidateVisibility, 
                                                   $iValidateIsConstant, 
                                                   $iValidateType, 
                                                   $ivalidateValue,  
                                                   $iValidateIsStatic )
   {
     // Logger::log("VariableM", "Method validateVariableArray started");
   	if ( $iVariableArray == null ) throw new \InvalidArgumentException("Variable description is null");
   	if ( !is_array($iVariableArray) ) throw new \InvalidArgumentException("Variable description is not array");
   	
   	if ( $iValidateName && (!key_exists('Name', $iVariableArray) || $iVariableArray['Name'] == null) ) throw new \InvalidArgumentException("Variable name is not defined");
   	if ( $iValidateVisibility && (!key_exists('Visibility', $iVariableArray) || $iVariableArray['Visibility'] == null) ) throw new \InvalidArgumentException("Variable visibility is not defined");
      if ( $iValidateIsStatic && (!key_exists('IsStatic', $iVariableArray) || $iVariableArray['IsStatic'] === null) ) throw new \InvalidArgumentException("Variable static value is not defined");
   	if ( $iValidateIsConstant && (!key_exists('IsConstant', $iVariableArray) || $iVariableArray['IsConstant'] === null) ) throw new \InvalidArgumentException("Variable constant value is not defined");
   	if ( $iValidateType && (!key_exists('Type', $iVariableArray) /* || $iVariableArray['Type'] == null // type can be empty for constructor for example */) ) throw new \InvalidArgumentException("Variable type is not defined");
   	if ( $ivalidateValue && !key_exists('Value', $iVariableArray) ) throw new \InvalidArgumentException("Variable value is not defined");
   }
   
   public function __construct( $iType, $iName, VisibilityM $iVisibility, $iIsConst, $iInitialValue, $iIsStatic )
   {
    //  Logger::log("VariableM", "Method __construct started");
      $this->mType = $iType;
      $this->mName = $iName;
      $this->mVisibility = $iVisibility;
      $this->mInitialValue = $iInitialValue;
      $this->mIsConst = $iIsConst;
      $this->mIsStatic = $iIsStatic;
   }
   
   public function getType()
   {
      return $this->mType;
   }
   
   public function getName()
   {
      return $this->mName;
   }
   
   public function getVisibility()
   {
      return $this->mVisibility;
   }
   
   public function getInitialValue()
   {
      return $this->mInitialValue;
   }
   
   public function getIsConst()
   {
      return $this->mIsConst;
   }

   public function getIsStatic()
   {
      return $this->mIsStatic;
   }
}