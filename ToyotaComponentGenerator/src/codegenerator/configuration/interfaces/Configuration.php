<?php

class Configuration
{
   protected $mData = null;
   protected $mValidator = null;
   
   public function __construct($data, Validator $validator)
   {
      $validator->ValidateOrThrow($data);
      $this->mData = $data[$validator::ROOT_ELEMENT];
      $this->mValidator = $validator;
   }
}
