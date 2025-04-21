const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const validator = require("validator");

const Schema = mongoose.Schema;

const employeSchema = new Schema({
  name: { type: String, required: true, trim: true },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  role: { type: String, enum: ['RH','employe']},
  token:{ type: String },
  resetCode: { type: String }, 
  resetCodeExpiration: { type: Date },


});

module.exports = mongoose.model('Employer', employeSchema);
