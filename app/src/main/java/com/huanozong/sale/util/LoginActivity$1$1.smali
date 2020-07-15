.class Lcom/huanozong/sale/util/LoginActivity$1$1;
.super Ljava/lang/Object;
.source "LoginActivity.java"

# interfaces
.implements Landroid/content/DialogInterface$OnDismissListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/huanozong/sale/util/LoginActivity$1;->handleMessage(Landroid/os/Message;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$1:Lcom/huanozong/sale/util/LoginActivity$1;


# direct methods
.method constructor <init>(Lcom/huanozong/sale/util/LoginActivity$1;)V
    .registers 2
    .param p1, "this$1"    # Lcom/huanozong/sale/util/LoginActivity$1;

    .prologue
    .line 18
    iput-object p1, p0, Lcom/huanozong/sale/util/LoginActivity$1$1;->this$1:Lcom/huanozong/sale/util/LoginActivity$1;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onDismiss(Landroid/content/DialogInterface;)V
    .registers 3
    .param p1, "paramAnonymous2DialogInterface"    # Landroid/content/DialogInterface;

    .prologue
    .line 21
    iget-object v0, p0, Lcom/huanozong/sale/util/LoginActivity$1$1;->this$1:Lcom/huanozong/sale/util/LoginActivity$1;

    iget-object v0, v0, Lcom/huanozong/sale/util/LoginActivity$1;->this$0:Lcom/huanozong/sale/util/LoginActivity;

    invoke-virtual {v0}, Lcom/huanozong/sale/util/LoginActivity;->finish()V

    .line 22
    return-void
.end method
