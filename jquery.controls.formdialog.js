/*
* Copyright (c) 2013 Applied Technologies, Ltd.
*
* Permission is hereby granted, free of charge, to any person obtaining
* a copy of this software and associated documentation files (the
* "Software"), to deal in the Software without restriction, including
* without limitation the rights to use, copy, modify, merge, publish,
* distribute, sublicense, and/or sell copies of the Software, and to
* permit persons to whom the Software is furnished to do so, subject to
* the following conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
* LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
* OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
* WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
*/
(function($, undefined){
  
	$.controls = $.controls || {};
	
	/*
	 * Inheritors should override 
	 */
	$.controls.FormDialog = $.controls.Dialog.extend({
		init: function(config) {
			this._super(config);
			
			this.initFields();
			this.resetForm();
			
			$.each(this.fields, function(i1, field) {
				$(['keyup', 'cut', 'paste', 'drop', 'blur']).each(function(i2, ename) {
					field.element.bind(ename, function(event) {
						if (this.isActive()) {
							var code = (event.keyCode ? event.keyCode : event.which);
							if (code == 13) { // RETURN pressed
								if (!this.config.ok.hasClass('disabled')) {
									this.submitForm();
									this.hide();
								}
							} else if (code == 27) { // ESC pressed
								this.resetForm();
								this.hide();
							} else {
								this.deferredCall(field, field.element.val(), 1000, field.validator);
							}
						};
					}.bind(this));
				}.bind(this));
			}.bind(this));
			
			this.on('ok', function() { this.submitForm(); }.bind(this));
			this.on('cancel', function() { this.resetForm(); }.bind(this));
			this.on('show', function() { this.activateForm(); }.bind(this));
		},
		
		initFields: function() {
			// Implement in inheritors
		},
		
		deferredCall : function(field, value, delay, runnable) {
			if (!this.locked) {
				if (field.currentValue != value) {
					field.currentValue = value;
					this.invalidateForm();
					if (runnable.timeout != null) {
						window.clearTimeout(runnable.timeout);
					}
					runnable.time = new Date().getTime();
					runnable.timeout = window.setTimeout(function() {
						if (this.isRelevant(runnable.time)) {
							runnable.bind(this)(field);
						}
						window.clearTimeout(runnable.timeout);
			        }.bind(this), delay);
				}
			}
		},
		
		isRelevant: function(scheduleTime) {
			return this.time == null || this.time == undefined || scheduleTime >= this.time;
		},
		
		checkField: function(field, status) {
			field.status = status;
			if (!field.status.success) {
				$('.tt', field.notification).text(status.message);
				field.notification.show();
			} else {
				$('.tt', field.notification).text('');
				field.notification.hide();
			}
		},
		
		activateForm: function() {
			// Implement in inheritors
		},
		
		submitForm: function() {
			// Implement in inheritors
		},
		
		checkForm: function() {
			var complete = true;
			var error = false;
			$.each(this.fields, function(index, field) {
				if (field.status == null) {
					complete = false;
				} else if (!field.status.success) {
					error = true;
				}
			});
			if (error) {
				this.config.messages.removeClass('checking incomplete complete').addClass('error');
			} else if (!complete) {
				this.config.messages.removeClass('checking error complete').addClass('incomplete');
			} else {
				this.config.messages.removeClass('checking incomplete error').addClass('complete');
			}
			if (error || !complete) {
				this.config.ok.addClass('disabled');
			} else {
				this.config.ok.removeClass('disabled');
			}
		},
		
		invalidateForm: function() {
			this.config.messages.removeClass('complete').removeClass('incomplete').removeClass('error').addClass('checking');
			this.config.ok.addClass('disabled');
		},
		
		resetForm: function() {
			$.each(this.fields, function(index, field) {
				field.status = null;
				field.currentValue = null;
				
				field.element.val(field.defaultValue);
				field.notification.children('.tt').text('');
				field.notification.hide();
			}.bind(this));
			
			this.config.messages.removeClass('checking').removeClass('incomplete').removeClass('error').addClass('complete');
			this.config.ok.addClass('disabled');
			
			this.time = new Date().getTime();
		}
	});
	
})(jQuery);
