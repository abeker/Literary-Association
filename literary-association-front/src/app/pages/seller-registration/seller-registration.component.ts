import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd';
import { BankService } from './../../services/bank.service';
import { LiteraryAssociationService } from './../../services/literary-association.service';
import { PaymentConcentratorService } from './../../services/payment-concentrator.service';

@Component({
  selector: 'app-seller-registration',
  templateUrl: './seller-registration.component.html',
  styleUrls: ['./seller-registration.component.css']
})
export class SellerRegistrationComponent implements OnInit {

    bitcoinChecked = false;
    paypalChecked = false;
    bankChecked = false;
    bankList: any[] = [{name: 'aca'}];
    selectedBank: string = "";
    bitcoinVisible: boolean = false;
    paypalVisible: boolean = false;
    bankVisible: boolean = false;

    validateForm!: FormGroup;

    constructor(private fb: FormBuilder,
                private bankService: BankService,
                private literaryAssociationService: LiteraryAssociationService,
                private paymentConcentratorService: PaymentConcentratorService,
                private message: NzMessageService) {}

    // ngOnDestroy(): void {
    //   alert('ondestroy');
    // }

    ngOnInit(): void {
      this.paymentConcentratorService.getPaymentTypes().subscribe(response => {
        console.log(response.paymentTypeNames);

        response.paymentTypeNames.forEach(paymentType => {
          if(paymentType === 'bitcoin') {
            this.bitcoinVisible = true;
          } if(paymentType === 'paypal') {
            this.paypalVisible = true;
          } if(paymentType === 'bank' || paymentType === 'bank1' || paymentType === 'unicredit'
                || paymentType === 'raiffeisen') {
            this.bankVisible = true;
          }
        });
      });

      this.validateForm = this.fb.group({
        sellerName: [null, [Validators.required, Validators.minLength(5)]],
        countryName: [null, [Validators.required]],
        cityName: [null, [Validators.required]],
        streetName: [null, [Validators.required]],
        zipName: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(5)]],
        bank: [null],
        membership: [null, [Validators.required]]
      });
    }

    submitForm(): void {
      for (const i in this.validateForm.controls) {
        this.validateForm.controls[i].markAsDirty();
        this.validateForm.controls[i].updateValueAndValidity();
      }
      const formValues = this.validateForm.value;
      this.literaryAssociationService.createLA({
        "name": formValues.sellerName,
        "country": formValues.countryName,
        "city": formValues.cityName,
        "streetNumber": formValues.streetName,
        "zipCode": formValues.zipName,
        "membershipAmount": formValues.membership
      }).subscribe(response => {
        console.log(response);
        if(response.luId) {
          this.createLUinKP(response.luId);
        }
      });
    }

    createLUinKP(luId): void {
      this.paymentConcentratorService.createLU(luId, {
        "paymentTypeNames": this.getPaymentTypeListFromSelectedCheckboxes()
      }).subscribe(response => {
        console.log(response);
        this.message.success('Successfuly created Seller Account');
      })
    }

    getPaymentTypeListFromSelectedCheckboxes(): string[] {
      let retList: string[] = [];
      if(this.bankChecked) {
        console.log(this.validateForm.value.bank);
        retList.push(this.validateForm.value.bank);
      } if (this.bitcoinChecked) {
        retList.push('bitcoin');
      } if(this.paypalChecked) {
        retList.push('paypal');
      }
      return retList;
    }

    bankClicked(): void {
      if(this.bankChecked){
        this.bankService.getBanks().subscribe(response => {
          console.log(response);
          this.bankList = [...response];
        });
      }
    }

}
