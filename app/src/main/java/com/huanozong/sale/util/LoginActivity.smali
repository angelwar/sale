.class public Lcom/huanozong/sale/util/LoginActivity;
.super Landroid/support/v7/app/AppCompatActivity;
.source "LoginActivity.java"


# instance fields
.field private handler:Landroid/os/Handler;

.field private handler1:Landroid/os/Handler;


# direct methods
.method public constructor <init>()V
    .registers 2

    .prologue
    .line 11
    invoke-direct {p0}, Landroid/support/v7/app/AppCompatActivity;-><init>()V

    .line 13
    new-instance v0, Lcom/huanozong/sale/util/LoginActivity$1;

    invoke-direct {v0, p0}, Lcom/huanozong/sale/util/LoginActivity$1;-><init>(Lcom/huanozong/sale/util/LoginActivity;)V

    iput-object v0, p0, Lcom/huanozong/sale/util/LoginActivity;->handler1:Landroid/os/Handler;

    return-void
.end method
