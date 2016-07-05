<?php
      
class Pack
{
	protected $mSize = null;
	protected $mData = null;

	public function __construct(&$iPack)
	{
		$this->mSize = $iPack['Size'];
		$this->mData = $iPack['Data'];
	}

	public function getSize()
	{
		return $this->mSize;
	}

	public function getData()
	{
		return $this->mData;
	}

}